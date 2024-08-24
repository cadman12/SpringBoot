package edu.pnu;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class Base64Main {

	public static void main(String[] args) {
		test01();
		test02();
	}

	private static void test02() {
		String encodedString;
		{
			Encoder encoder = Base64.getEncoder();
			
			String src = "{\"alg\": \"HS256\", \"typ\": \"JWT\"}";
			byte[] res = encoder.encode(src.getBytes());
			encodedString= new String(res);
			
			System.out.println("encodedString:\"" + src + "\"==>\"" + encodedString + "\""); 
		}
		{
			Decoder decoder = Base64.getDecoder();
			
			byte[] res = decoder.decode(encodedString);
			System.out.println("decodedString:" + new String(res));			
		}
	}
	
	private static void test01() {
		String str = "ABCD";
		Encoder encoder = Base64.getEncoder();
		byte[] b1 = encoder.encode(str.getBytes());
		String encodedString = new String(b1);
		System.out.println("encodedString:\"" + str + "\"==>\"" + encodedString + "\""); 
		System.out.println("Base64는 기본 3byte 단위로 데이터를 묶어서 처리한다.");
		System.out.println("ABCD는 4byte이므로, encoder는 3의 배수 중 4보다 크면서 가장 작은 값인 6byte로 encoding");
		System.out.println("A(65)   B(66)   C(67)   D(68)");
		System.out.println("010000010100001001000011010001000000000000000000");
		System.out.println("Q(16) U(20) J(9)  D(3)  R(17) A(0)  =     =");

		System.out.println("-".repeat(40));

		Decoder decoder = Base64.getDecoder();
		byte[] b2 = decoder.decode(encodedString);
		System.out.println("decodedString:" + new String(b2));
		System.out.println("=".repeat(40));
	}
}
