# JPA 설정

	- 이클립스 프로젝트 [Properties]-[Project facets]에 JPA가 없는 경우
	
		. [Help]-[Install New Software...]에서 "JPA"를 검색해서 추가 설치하면 됨.
	
		. 설치 중 에러가 발생하면 "Contact all update sites during install to find required software" 체크를 해제하고 창을 닫은 뒤 다시 시도
	
		. 그럼에도 에러가 발생하면 "Work With:"를 하나씩 선택하면서 순서대로 다시 시도 (대체로 "Eclipse Repository"를 선택하면 성공가능성이 높음)

# Persistence.xml 생성

	- [Project facets]에서 JPA 설정을 추가하면 자동으로 META-INF/persistence.xml 파일이 생성됨.

# JPA 질의 예제

	- H2 데이터베이스
	
		. 프로그램을 실행하기 전에 jdbc:h2:tcp://localhost/~/.h2/JPALecture 데이터베이스를 생성해줘야 함.

# Entity Class

	- Member.java
	
	- Board.java

# JPAClient.java

	- insertData
	
		. 예제 데이터 입력

	- jpqltest
	
		. JPQL을 이용한 질의 메소드

	- nativequerytest
	
		. Native Query를 이용한 질의 메소드

# 기타

	- src/main/resources, src/test/* 등의 폴더가 생성되는 것은 프로젝트를 만들 때, Maven Project로 생성하면 자동으로 만들어지는 폴더 구조이다.
	
	- 만약 Java project로 생성해서 Maven Project로 변경하는 경우에는 생성되지 않는다.