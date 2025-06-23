package edu.pnu;

import edu.pnu.jwt.JwtLib;

public class AppClient {
	
	public static void main(String[] args) {
		String token = JwtLib.create("edu.pnu.jwt")
				.periodSecond(100)
				.claim("username", "member")
				.claim("role", "ROLE_MEMBER")
				.build();

		if (token != null)
			System.out.println(token);
	}
}
