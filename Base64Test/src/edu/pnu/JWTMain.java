package edu.pnu;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

// JWT.io에서 인코딩 확인
public class JWTMain {

	private static void printToken(String str) {
		String[] arr = str.split("\\.");
		for (String s : arr) {
			System.out.println(s);
		}
		System.out.println("=".repeat(90));
	}
	
	/*
	 * secret base64 encoded ==> not check
	 * eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
	 * eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.
	 * vsf9EwyLprqAdy4lQqJDNCxb8fSY2g8LtC7A3MmB8ZM
	 *
	 * secret base64 encoded ==> check
	 * eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
	 * eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.
	 * UzCXUXZAocAu_v4EJP7hjOj9voUja7363QrHkEUyOiI
	 */
	public static void main(String[] args) throws Exception {

		String header    = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
		String payload   = "{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"iat\":1516239022}";
		String algorithm = "HmacSHA256";
		String secretKey = "edu.pnu";
		String secretKeyBase64Encoded = "ZWR1LnBudQ==";	// jwt.io에서 secret base64 encoded를 체크한 경우 입력 secretKey 

		Encoder encoder = Base64.getEncoder();
		Decoder decoder = Base64.getDecoder();
		
		String encodedString1 = encoder.withoutPadding().encodeToString(header.getBytes());
		String encodedString2 = encoder.withoutPadding().encodeToString(payload.getBytes());
		
		System.out.println("Header:" + header);
		System.out.println("\t==>" + encodedString1);
		System.out.println("-".repeat(90));
		
		System.out.println("Payload:" + payload);
		System.out.println("\t==>" + encodedString2);
		System.out.println("=".repeat(90));

		// 암호화 객체 생성
		Mac hs256 = Mac.getInstance(algorithm);
		
		System.out.println("Secret base64 NOT encoded");
		System.out.println("-".repeat(50));
		// secret key, 인코딩 알고리즘 설정
		hs256.init(new SecretKeySpec(secretKey.getBytes(), algorithm));
		
		String jwtSrc = encodedString1 + "." + encodedString2;
		String str1 = jwtSrc + "." + encoder.withoutPadding().encodeToString(hs256.doFinal(jwtSrc.getBytes()));
		
		printToken(str1);
		
		System.out.println("Secret base64 encoded");
		System.out.println("-".repeat(50));
		// secret key, algorithm 설정 ==> secret key가 Base64 Encoding 되어 있을 경우
		byte[] bytes = decoder.decode(secretKeyBase64Encoded.getBytes());	// 우선 Base64로 인코딩된 secret key를 디코딩
		hs256.init(new SecretKeySpec(bytes, algorithm));
		
		String str2 = jwtSrc + "." + encoder.withoutPadding().encodeToString(hs256.doFinal(jwtSrc.getBytes()));

		printToken(str2);
 	}
}
