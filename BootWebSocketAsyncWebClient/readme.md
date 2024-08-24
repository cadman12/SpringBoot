## 개요

- 1. 부트 서버를 시작하면

- 2. 웹소켓 서버를 만들어서 대기하면서

- 3. 동시에 부트 서버 API를 주기적으로 동기 및 비동기로 호출하는 스케쥴링을 시작하고

- 4. PostMan에서 연결 웹소켓에 연결한 뒤에 정보를 보내면

- 5. 현재 연결된 웹소켓 클라이언트들에게 정보를 푸쉬한다.

---

## 실행 순서

- 1. 부트 서버를 구동한다.

- 2. PostMan에서 WebSocket 테스트를 위한 탭을 만든다.

		. New - WebSocket
		. ws://localhost:8080/pushservice
		. Connect
		. Scheduling 기능이 활성화되어 있으면 1초간격으로 데이터가 console 창에 출력된다.

- 3. PostMan - Message 탭에서 데이터 입력 - Send
	
		. 부트 console에 출력되면 정상

---

## 웹소켓 설정 순서

- TextWebSocketHandler와 인터페이스 WebSocketConfigurer를 상속한 클래스를 만들어서 빈 객체로 등록한다. (AppWebSocketConfig)


- Socket 연결명 설정을 위해 registerWebSocketHandlers를 오버라이드해서 작성

		. ws://localhost:8080/pushservice	==> "/pushservice"


- Socket 접속/해제, 메시지 송수신을 위한 메소드를 오버라이드해서 작성

		. public void afterConnectionEstablished(WebSocketSession session)
		. public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
		. protected void handleTextMessage(WebSocketSession session, TextMessage message)
	
---

## 푸시 서비스 테스트를 위한 스케쥴링 코드

- 추가로 임의의 에뮬레이터가 있어서 주기적으로 부트에게 정보를 보내주는 것을 시뮬레이션하기 위해 Scheduling 기능을 활용한다.

- 메인 클래스에 Boot Scheduling 활성화를 위한 어노테이션을 추가

		. @EnableScheduling

- RestTemplate을 이용해서 서버의 API를 동기로 호출한다.

		. scheduledPostReportingwithSync
		. @Scheduled을 이용해서 주기적으로 데이터를 서버로 전송하도록 설정한다.

- WebClient를 이용해서 서버의 API를 비동기로 호출한다.

		. scheduledPostReportingwithAsync
		. @Scheduled을 이용해서 주기적으로 데이터를 서버로 전송하도록 설정한다.
		. WebFlux를 사용하기 위해 디펜던시를 추가한다.
			- spring-boot-starter-webflux
		. 현재는 객체 하나를 넘기는 예제가 포함되어 있다.
		. 비동기 호출이므로 필요에 따라 응답을 처리하는 콜백 핸들러를 한다.
