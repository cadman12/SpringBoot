# Base64 인코딩 테스트 샘플 프로젝트

	- Base64Main : 문자열과 JSON Base64 인코딩/디코딩

	- JWTMain : JSON Web Token 인코딩/디코딩

	- jwt.io에서 secret base64 encoded를 체크가 된 경우에는 입력되는 secretKey가 Base64로 인코딩된 값이라는 의미
	
		. "edu.pnu"가 원본 secretKey인 경우 이 값을 Base64로 인코딩하면 "ZWR1LnBudQ=="가 나옴.
		
		. 이 값을 secretKey에 입력하고 secret base64 encoded를 체크한 상태로 인코딩하면 동일한 결과가 나옴