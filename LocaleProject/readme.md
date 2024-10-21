# Locale 설정

- Locale 설정에 따라 숫자, 금액, 날짜, 시간 출력 포멧이 바뀌어서 출력하는 예제

### Locale을 변경하는 방법

1.application.properties

	spring.web.locale=en_US
	spring.web.locale-resolver=fixed

2.Bean 객체

	WebMvcConfigurer를 구현한 클래스 CustomConfig에서 설정

	==> Bean 객체와 applicaiton.properties 설정이 모두 존재하면 Bean 객체가 우선한다.
