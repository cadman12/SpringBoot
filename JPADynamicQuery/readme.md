# 동적 질의 테스트

- 테이블에 대해 검색할 때 검색 조건이 동적으로 변경되는 경우에 사용할 수 있는 예제 코드

- 현재 예제에서는 Specification을 활용한 예제이며 Board에 대해 검색할 때

	. http://localhost:8080/board?title=tom1
			또는
	. http://localhost:8080/board?title=tom1&content=tom10

	로 검색하면 조건에 따라 결과를 리턴한다.

### 1.QueryDSL을 이용하는 방법

- 교재에 기술되어 있는 방법 (QueryDslLecture 참조)

- QueryDSL을 DI 받은 뒤 자동 생성되는 질의 객체를 이용하는 방법

### 2.Specification<T> 을 이용하는 방법

- http://localhost:8080/board?title=tom1&content=tom10

- 대상 엔티티의 레포지토리에 JpaSecificationExcecuter<T>를 추가

- 대상 엔티티에 대한 검색 조건들을 지정

- Service에서 Specification<T> 객체를 만들어서 검색 조건들을 입력

- 레포지토리의 findAll 메서드의 파라미터로 Spec 객체를 전달

### 3.Criteria API를 이용하는 방법

- http://localhost:8080/board1?title=tom1&content=tom10

- Service에서 EntityManager 객체를 DI 받아 CriteriaBuilder 객체를 이용해서 검색 조건을 설정한 뒤 검색 실행

### 4.Native Query가 포함된 JPA를 이용하는 방법

- http://localhost:8080/board2?title=tom1&content=tom10

- Service에서 EntityManager 객체를 DI 받아 Query 객체를 이용해서 검색 조건을 설정한 뒤 검색 실행

### 5.JdbcTemplate를 이용한 Native Query를 이용하는 방법

- http://localhost:8080/board3?title=tom1&content=tom10

- Service에서 JdbcTemplate 객체를 DI 받아 검색 조건을 설정한 뒤 검색 실행

- JPA를 사용하지 않기 때문에 연관 객체 정보를 가져오기 위해서는 별도로 Join 질의를 해야 한다. 예제에서는 Join 질의까지는 하지 않고 있다.

- http://localhost:8080/board4?title=tom1&content=tom10 ==> Join 질의 버전
