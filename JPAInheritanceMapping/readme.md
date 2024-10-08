# JPA 상속

##0. 상속관계 매핑 정리

	. 데이터베이스가 확장 가능성이 높고 복잡한 경우 조인 전략을 선택
	. 반면에 확장 가능성 적으면서 심플한 구조가 필요할 경우 단일 테이블 전략으로 선택
	. 조인 전략과 단일 테이블 전략의 trade-off를 생각해서 전략을 선택
	. table-per-class 전략은 두 경우 모두 불리하므로 대상에서 제외

##1. JOINED

	. 부모클래스와 자식 클래스 모두 테입블로 생성
	. 부모클래스에만 ID 설정
	. 테이블이 만들어질 때 부모테이블과 자식테이블에 동시에 기본키로 생성
	. 데이터 검색 시에는 조인됨
	. @DiscriminatorColumn(name="DTYPE") / @DiscriminatorValue("A")
		. 자식테이블의 데이터 타입을 지정하는 필드. 필드명을 지정하지 않으면 디폴트로 "DTYPE"이 지정됨.
		. 부모테이블에 DTYPE 필드가 생성되어 자식클래스 명이 입력되거나 자식 클래스에서 지정한 값이 입력됨.
		. 자식 클래스에서 지정하려면 @DiscriminatorValue를 사용해서 지정. 디폴트는 자식 클래스명

##2. SINGLE_TABLE

	. 부모클래스로 하나의 테이블만 생성
	. 데이터입력 시 비어있는 필드는 모두 NULL로 입력 처리됨.

##3. TABLE_PER_CLASS

	. 자식 클래스만 부모 클래스의 필드를 추가해서 한 개의 테이블로 생성
		. 따라서 자식 클래스 필드인 DTYPE 설정이 필요하지 않다.
		. 이때 부모 클래스는 테이블이 생성되지 않아야 하므로 추상클래스로 지정
		. 추상클래스로 지정하지 않으면 데이터는 입력되지 않지만 테이블만 생성된다.
	. 키 생성 전략은 GenerationType.AUTO 로 설정해야 에러가 발생하지 않음.
	. AUTO를 사용하면 시퀀스 테이블을 사용하는 것으로 설정되어 시퀀스 테이블이 추가 생성된다.

##MySQL

	. jdbc:mysql://localhost:3306/inheritancemapping
	. user : scott
	. pass : tiger
