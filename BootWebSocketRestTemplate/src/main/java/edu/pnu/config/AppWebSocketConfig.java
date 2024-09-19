package edu.pnu.config;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.dto.PushDTO;

@Configuration
@EnableWebSocket	// Boot WebSocket 활성화
public class AppWebSocketConfig extends TextWebSocketHandler implements WebSocketConfigurer  {

	// 연결된 클라이언트들을 저정하는 Set
	private static Set<WebSocketSession> clients = Collections.synchronizedSet(new HashSet<WebSocketSession>());

	@Value("${edu.pnu.jwt.key}")
	private String jwtKey;
	
	// WebSocket 연결명 설정 (http://localhost:8080/pushservice) ==> WebSocketConfigurer
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(this, "pushservice").setAllowedOrigins("*");
	}

	// 웹소켓 연결 URI에서 파라미터들만 추출해서 map으로 리턴
//	private Map<String, List<String>> splitQuery(String query) throws Exception {
//        return Arrays.stream(query.split("&"))
//                .map(param -> param.split("="))
//                .collect(Collectors.groupingBy(
//                        param -> URLDecoder.decode(param[0], StandardCharsets.UTF_8),
//                        Collectors.mapping(
//                                param -> param.length > 1 ? URLDecoder.decode(param[1], StandardCharsets.UTF_8) : "",
//                                Collectors.toList()
//                        )
//                ));
//    }
	
	// Client가 접속 시 호출되는 메서드 (파라미터로 추가 정보로 사용자명이 넘어오는 경우)
//	@Override
//	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//		URI uri = session.getUri();
//        
//        // Extract the query parameters from the URI
//		String username = "Anonymous";
//        if (uri != null) {
//            String query = uri.getQuery(); // e.g., "username=abcd"
//            
//            // Parse the query parameters
//            Map<String, List<String>> queryParams = splitQuery(query);
//            
//            // Extract the username parameter
//            List<String> usernames = queryParams.get("username");
//            if (usernames != null && !usernames.isEmpty()) {
//                username = usernames.get(0);
//            }
//        }		
//        System.out.println("클라이언트 접속[username]: " + username);
//		clients.add(session);
//	}
	
	// 헤더에서 인증헤더만 추출해서 리턴
	private String extractTokenFromHeaders(HttpHeaders headers) {
        List<String> authorizationHeaders = headers.get(HttpHeaders.AUTHORIZATION);
        if (authorizationHeaders != null && !authorizationHeaders.isEmpty()) {
            String bearerToken = authorizationHeaders.get(0);
            // 토큰이 "Bearer "로 시작하면 추출
            if (bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7); // "Bearer " 이후의 토큰만 추출
            }
        }
        return null;
    }	
	
	// Client가 접속 시 호출되는 메서드 (헤더에 추가 정보로 사용자명이 넘어오는 경우)
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		HttpHeaders headers = session.getHandshakeHeaders();
		
		// Extract the query parameters from the URI
		String username = "Anonymous";

		// 헤더에서 JWT 토큰을 추출 (예: Authorization: Bearer <token>)
        String jwtToken = extractTokenFromHeaders(headers);

        if (jwtToken != null && !jwtToken.isEmpty()) {
            // JWT 토큰을 파싱하여 사용자 정보 추출 코드가 추가되면 됨.
//        	Claim claim = JWT.require(Algorithm.HMAC256(jwtKey)).build().verify(jwtToken).getClaim("username");
//
//            if (claim != null) {
//            	username = claim.toString();
//                // 사용자 정보를 WebSocket 세션에 속성으로 저장 (또 사용할 수 있는 경우를 대비해서)
//                session.getAttributes().put("username", username);
//            }
        	// 테스트용으로 "Bearer user"를 Authorization Header에 설정해서 웹소켓 연결 
        	username = jwtToken;
        	session.getAttributes().put("username", username);
        }
		System.out.println("클라이언트 접속[username]: " + username);
		clients.add(session);
	}
	
	// Client가 접속 해제 시 호출되는 메서드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println(session + " 클라이언트 접속 해제");
		clients.remove(session);
	}		

	// Client에서 메시지가 왔을 때 호출되는 메서드 ==> 채팅과 같은 형태의 기능을 추가하지 않는다면 필요없는 메소드이다.
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("Message : " + message.getPayload());
	}
	
	// FE에게 정보를 푸시하는 메소드
	public void sendPushMessage(PushDTO pushDto) {
		// 연결된 클라이언트가 없으면 그냥 리턴
	    if (clients.size() == 0)	return;

	    // 자바 객체를 JSON 문자열로 변환
	    ObjectMapper objectMapper = new ObjectMapper();
	    String msg;
		try {
			msg = objectMapper.writeValueAsString(pushDto);
		} catch (JsonProcessingException e) {
			System.out.println("JSON Error:" + e.getMessage());
			return;
		}

		// FE에 전송할 JSON 메시지객체 생성
		TextMessage message = new TextMessage(msg);

		// 블럭안에 코드를 수행하는 동안 map 객체에 대한 다른 스레드의 접근을 방지한다.
		synchronized (clients) {
		    for(WebSocketSession sess: clients) {
		    	try {
		    		sess.sendMessage(message);
		    	} catch (IOException e) {
		    		System.out.println(sess.getRemoteAddress() + ":" + e.getMessage());
		    	}
		    }
		}
	}
}
