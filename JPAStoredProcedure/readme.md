# Stored Procedure

	- 데이터베이스에서 선언한 저장 프로시저를 사용하는 예제 코드
	
	- MySQL의 샘플 데이터베이스인 "sakila"의 저장 프로시저 "film_in_stock"를 사용해서 작성

	- 저장 프로시저에 Input/Output 파라미터가 모두 있으면서, ResultSet도 같이 있을 경우에 대해 모두 처리가 가능하도록 예제 작성
	
	- 사용자 인터페이스(InventoryRepositoryCustom)에 메서드를 정의해두고 구현체인 InventoryRepositoryImpl를 작성
	
	- JPA Repository 인터페이스(InventoryRepository)를 정의할 때 사용자 인터페이스를 상속하도록 설정하면 구현체를 만들때 InventoryRepositoryImpl을 결합
	
	- 이때 자동으로 DI가 수행되므로 InventoryRepositoryImpl에 @Repository를 선언할 필요는 없음.


# edu.pnu.jdbc.JDBCStoredProcedure.java

	- JDBC를 이용한 저장 프로시저 실행 예제 코드