package edu.pnu;

import edu.pnu.jwt.JwtLib;

public class AppClient {
	
	static String secret = "edu.pnu.jwt";
	
	public static void main(String[] args) {
		String token = JwtLib.create(secret)
				.algorithm("HS256")
				.periodSecond(100)
				.claim("username", "member")
				.claim("role", "ROLE_MEMBER")
				.build();

		System.out.println(token);
		if (token == null) return;
		
		String username = (String)JwtLib.parse(token, secret).getClaim("username");
		String role = (String)JwtLib.parse(token, secret).getClaim("role");
		Boolean expired = JwtLib.parse(token, secret).isExpired();
		System.out.println("username:" + username);
		System.out.println("role    :" + role);
		System.out.println("expired :" + expired);
	}
}
