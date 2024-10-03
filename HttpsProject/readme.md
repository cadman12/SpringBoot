# Spring Boot 서버에서 자체 서명된 SSL 인증서를 사용한 테스트 방법

1.자체 서명된 인증서 생성

		아직 생성하지 않은 경우 자체 서명된 인증서를 생성해야 합니다.
		OpenSSL을 사용하여 인증서를 생성하고 이를 Spring Boot에서 사용할 수 있는 JKS(Java Keystore) 형식으로 변환할 수 있습니다.

		OpenSSL로 인증서 생성
		개인 키 생성:

		sh
		코드 복사
		openssl genpkey -algorithm RSA -out server.key -pkeyopt rsa_keygen_bits:2048
		인증서 서명 요청(CSR) 생성:

		sh
		코드 복사
		openssl req -new -key server.key -out server.csr
		자체 서명된 인증서 생성:

		sh
		코드 복사
		openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt
		인증서와 키를 PKCS12 형식으로 변환
		Spring Boot는 Java Keystore 형식을 사용하므로 인증서를 PKCS12 형식으로 변환해야 합니다.

		PKCS12로 변환

		:

		sh
		코드 복사
		openssl pkcs12 -export -in server.crt -inkey server.key -out server.p12 -name "selfsigned"

2.JKS(Java 키 저장소) 생성

		PKCS12 파일을 Java Keystore로 가져와야 합니다.

		JKS 키 저장소로 가져오기

		:

		sh
		코드 복사
		keytool -importkeystore -srckeystore server.p12 -srcstoretype pkcs12 -destkeystore server.jks -deststoretype JKS -alias selfsigned

3.키 저장소를 사용하도록 Spring Boot 구성

		키 저장소를 사용하려면 application.properties 또는 application.yml 파일을 업데이트하세요.

		application.properties 사용:
			properties
			코드 복사
			server.port=8443
			server.ssl.key-store=classpath:server.jks
			server.ssl.key-store-password=your_keystore_password
			server.ssl.key-password=your_key_password
		application.yml 사용:
			yaml
			코드 복사
			server:
			  port: 8443
			  ssl:
			    key-store: classpath:server.jks
			    key-store-password: your_keystore_password
		   		 key-password: your_key_password

4.클래스 경로에 키 저장소 배치

		server.jks 파일이 클래스 경로(일반적으로 Spring Boot 프로젝트의 src/main/resources 디렉토리)에 있는지 확인하세요.

5.Spring Boot 애플리케이션 실행

		Spring Boot 애플리케이션을 시작하고 https://localhost:8443을 통해 액세스하세요.

6.자체 서명된 인증서 신뢰(로컬 테스트용)

		브라우저 경고를 방지하려면 자체 서명된 인증서를 수동으로 신뢰해야 합니다.

		Windows의 경우:
			certmgr.msc를 엽니다.
			"신뢰할 수 있는 루트 인증 기관" 스토어를 마우스 오른쪽 버튼으로 클릭하세요.
			"가져오기"를 선택하고 마법사를 따라 server.crt를 가져옵니다.
		macOS의 경우:
			키체인 액세스를 엽니다.
			server.crt 파일을 "시스템" 키체인으로 끌어다 놓습니다.
			인증서를 두 번 클릭하고 "항상 신뢰"로 설정합니다.
		Linux의 경우:
			인증서를 다음 위치에 복사하세요.

			/usr/local/share/ca-certificates/

		:

		sh
		코드 복사
		sudo cp server.crt /usr/local/share/ca-certificates/server.crt
		CA 인증서를 업데이트합니다.

		sh
		코드 복사
		sudo update-ca-certificates

		요약
		다음 단계에 따라 HTTPS 연결 테스트를 위해 자체 서명된 SSL 인증서를 사용하도록 Spring Boot 애플리케이션을 구성할 수 있습니다.
		이 설정은 Spring Boot 애플리케이션이 개발 또는 테스트 환경에서 SSL/TLS 연결을 적절하게 처리할 수 있도록 보장합니다.
