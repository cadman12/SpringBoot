package edu.pnu.jwt;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class JwtLib {
	
	public static JwtLibBuilder create(String secret) {
		return new JwtLibBuilder(secret);
	}

	public static JwtLibParser parse(String jwtToken, String secret) {
		try {
			return new JwtLibParser(jwtToken, secret);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////

	// 대칭키(HMAC) 기반 알고리즘 : Hash-based Message Authentication Code + Secured Hash Algorithm(해시함수 with NSA)
	//		HS256 : HMAC + SHA-256 ==> Default
	//		HS384 : HMAC + SHA-384
	//		HS512 : HMAC + SHA-512
	static String getHashAlgorithm(String alg) {
		if (alg.equalsIgnoreCase("HS384")) return "HmacSHA384";
		if (alg.equalsIgnoreCase("HS512")) return "HmacSHA512";
		return "HmacSHA256";
	}
	
	// bytes를 Base64 인코딩한 문자열을 리턴
	// remove : 제일 마지막에 추가 문자 "=" 제거 여부
	static String convertToBase64(byte[] bytes, boolean remove) throws Exception {
		String ret = Base64.getUrlEncoder().encodeToString(bytes);
		if (remove)	ret = ret.replace("=", "");
    	return ret;
	}

	// str을 Base64 인코딩한 문자열을 리턴
	// remove : 제일 마지막에 추가 문자 "=" 제거 여부
	static String convertToBase64(String str, boolean remove) throws Exception {
		byte[] bytes = str.getBytes("UTF-8");
		return convertToBase64(bytes, remove);
	}

	// Base64 인코딩한 문자열을 디코딩해서 리턴
	static String convertBase64ToString(String str) throws Exception {
		byte[] bytes = Base64.getUrlDecoder().decode(str);
		String ret = new String(bytes, "UTF-8");
		return ret;
	}
	
	// 주어진 알고리즘과 키값으로 암호화해서 문자열 리턴 
	static String makeSignature(String msg, String key, String algorithm) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), algorithm);
		Mac mac = Mac.getInstance(algorithm);
		mac.init(secretKeySpec);
		byte[] bytes = mac.doFinal(msg.getBytes("UTF-8"));
		return convertToBase64(bytes, true);
	}
	
	// jwt secret는 최소 32byte 이상이어야 한다. secret 길이가 32 이하면 (32-len) 만큼 *를 붙여준다.
	// edu.pnu.jwt ==> edu.pnu.jwt*********************
	static String makeSecret32(String secret) throws Exception {
		if (32 <= secret.length())	return secret;
		StringBuilder sb = new StringBuilder(secret);
		for (int i = secret.length() ; i < 32 ; i++)	sb.append("*");
		return sb.toString();
	}

	// map에 입력된 claim을 JSON 문자열로 변환한다.
	static String claimsToJSONString(Map<String, Object> claims) throws Exception {
		StringBuilder sb = new StringBuilder("{");
		Set<String> keys = claims.keySet();
		for(String key : keys) {
			Object value = claims.get(key);
			if (1 < sb.length()) sb.append(",");
			if (value instanceof String) {
				sb.append(String.format("\"%s\":\"%s\"", key, value));
			} else {
				sb.append(String.format("\"%s\":%s", key, value));
			}
		}
		sb.append("}");
		return sb.toString();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	public static class JwtLibParser {
		private String jwtToken;
		private String[] jwtArr;
		private String alg = "HS256";			// Default 값 설정
		private Map<String, String> map;
		
		public JwtLibParser(String jwtToken, String secret) throws Exception {
			try {
				this.jwtToken = jwtToken;
				
				jwtArr = this.jwtToken.split("\\.");
				
				// JWT 토큰이 유효한 토큰인지 검증 (signature 검증)
				String secret32 = makeSecret32(secret);
				String sign = makeSignature(jwtArr[0] + "." + jwtArr[1], secret32, getHashAlgorithm(alg));
	
				if (!sign.equals(jwtArr[2]))
					throw new RuntimeException("Signature is Invalid!");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		public String getClaim(String claim) {
			if (map != null) {
				return map.get(claim);
			}

			map = new HashMap<>();

			String str = "";
			try {
				// {"role":"ROLE_MEMBER","exp":1750651698,"iat":1750651598,"username":"member"}
				str = convertBase64ToString(jwtArr[1]);
				// "role":"ROLE_MEMBER","exp":1750651698,"iat":1750651598,"username":"member}"
				str = str.replaceAll("\\{", "");
				// "role":"ROLE_MEMBER","exp":1750651698,"iat":1750651598,"username":"member"
				str = str.replaceAll("\\}", "");
				// role:ROLE_MEMBER,exp:1750651698,iat:1750651598,username:member
				str = str.replaceAll("\"", "");

				String[] arr = str.split(",");
				for(String s : arr) {
					String[] tarr = s.split(":");
					map.put(tarr[0],  tarr[1]);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			return map.get(claim);
		}
		
		// JWT 토큰이 유효한 토큰인지 검증 (유효기간 이내인지 검증)
		public Boolean isExpired() {
			try {
				String expStr = getClaim("exp");
				if (Long.parseLong(expStr) < System.currentTimeMillis()*1000)
					return false;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}			
			return true;
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	public static class JwtLibBuilder {
		private String alg = "HS256";			// Default 값 설정
		private long periodSecond = 100 * 60;	// Default 값 설정
		private String secret;
		private Map<String, Object> claims = new HashMap<>();

		public JwtLibBuilder(String secret) {
			this.secret = secret;
		}

		public JwtLibBuilder algorithm(String alg) {
			this.alg = alg.toUpperCase();
			return this;
		}
		
		// 토큰 유효기간 설정 (second)
		// 토큰을 생성할 때 UnixTimeStamp에 periodSecond를 더해서 설정
		public JwtLibBuilder periodSecond(long periodSecond) {
			this.periodSecond = periodSecond;
			return this;
		}
		
		// payload에 담을 데이터 설정
		public JwtLibBuilder claim(String key, Object value) {
			this.claims.put(key, value);
			return this;
		}

		public String build() {
			try {
				String header = String.format("{\"alg\": \"%s\",\"typ\": \"JWT\"}", alg);
				String header64 = convertToBase64(header, true);
	
				long currentUnixTime = System.currentTimeMillis()/1000;
				claims.put("iat", currentUnixTime);
				claims.put("exp", currentUnixTime + periodSecond);
				
				String payload = claimsToJSONString(claims);
				String payload64 = convertToBase64(payload, true);
				
				String secret32 = makeSecret32(secret);
				String sign = makeSignature(header64 + "." + payload64, secret32, getHashAlgorithm(alg));
	
				return header64 + "." + payload64 + "." + sign;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return null;
		}
	}
}
