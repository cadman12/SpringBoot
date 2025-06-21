package edu.pnu;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class AppClient
{
	private static String removeWhiteSpace(String str) {
		return str.replaceAll("[\\n\\r\\t ]",  "");
	}

	private static String convertToBase64(byte[] bytes, boolean remove) {
		String ret = "Error";
    	try {
			ret = Base64.getUrlEncoder().encodeToString(bytes);
			if (remove)	ret = ret.replace("=", "");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	return ret;
	}
	
	private static String convertToBase64(String str, boolean remove) {
		byte[] bytes;
		try {
			bytes = str.getBytes("UTF-8");
			return convertToBase64(bytes, remove);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "Error";
	}
	
	//HMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload), 사용자키)
	private static String makeSignature(String msg, String key) {
		String ret = "Error";
		try {
			String algorithm = "HmacSHA256";
	        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), algorithm);
			
			Mac mac = Mac.getInstance(algorithm);
			mac.init(secretKeySpec);
			byte[] bytes = mac.doFinal(msg.getBytes("UTF-8"));
			ret = convertToBase64(bytes, true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return ret;
	}
	
    public static void main( String[] args ) throws UnsupportedEncodingException {
    	String header = ""
    				+ "	{"
    				+ "		\"alg\": \"HS256\","
    				+ " 	\"typ\": \"JWT\""
    				+ "	}";
    	header = removeWhiteSpace(header);
    	String header64 = convertToBase64(header, true);
    	System.out.println("header:" + header);
    	System.out.println(header64);
    	
    	String payload = ""
				    + "	{"
					+ "		\"username\": \"member\","
					+ "		\"iat\": 1718946900,"
					+ "		\"exp\": 1718950500"
					+ "	}";
    	payload = removeWhiteSpace(payload);
    	String payload64 = convertToBase64(payload, true);
    	System.out.println("payload:" + payload);
    	System.out.println(payload64);

    	String sign = makeSignature(header64 + "." + payload64, "edu.pnu.jwt123456789012345678901");
    	
    	System.out.println("Bearer " + header64 + "." + payload64 + "." + sign);
    }
}
