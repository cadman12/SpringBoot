# JPA 복합키 예제 (@IdClass)

- https://blog.naver.com/geodb/222829614676

- H2 데이터베이스

	. 프로그램을 실행하기 전에 jdbc:h2:tcp://localhost/~/.h2/JPALecture 데이터베이스를 생성해줘야 함.

# Entity Class

- Member.java

- Board.java

	. 연관관계 객체 선언할 때 @JoinColumns를 이용해서 연관관계 컬럼을 정의해 주여야 한다.

# IdClass

- MemberID.java

	. persistence.xml에 선언할 필요 없음.
	. @EmbeddedId를 사용하는 경우에는 persistence.xml에 선언해 주어야 한다.

# JPAClient.java

- insertData

	. 예제 데이터 입력

- jpqltest

	. JPQL을 이용한 질의 메소드
