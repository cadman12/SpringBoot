# N+1 문제

## 문제 내용

	- JPA에서 엔티티간 연관관계를 맺게 되면 불필요한 추가적인 질의가 발생하는 문제로서 흔히 발생하는 성능 문제이다.

	- 예를 들자면
	
	1. 엔티티 Member와 엔티티 Board가 서로 연관관계가 설정되어 있다.
	
	2. Board 데이터 검색을 위한 질의 한 개를 실행
	
	3. 검색 결과가 10개라면, 검색된 10개의 Board 데이터에 연관된 각각의 Member 데이터를 읽어 들이기 위한 질의가 10번 추가로 발생해서 총 (1 + 10)개의 질의가 발생하는 문제.

## 해결책

###1. Fetch Join
	
	- JPQL의 fetch join을 이용하는 방법으로서 엔티티를 조회할 때 연관 엔티티까지 동시에 조회하도록 한다.
	
	- Eager 타입으로 설정한 것과 같은 효과를 낸다. 다만 Eager 타입은 전역적인 영향을 미치는데 반해 fetch join은 질의 레벨에만 영향을 미친다는 차이점이 있다.
	
	@Query("SELECT b FROM Board b JOIN FETCH b.member where b.visitCount>:1")
	List<Board> getBoardByCnt(int cnt);
	
	@Query("SELECT m FROM Member m JOIN FETCH m.boardList")
	List<Member> getMemberWithBoardList();
	
	- 단점
	
	1. 질의 한번에 모든 데이터를 가져오기 때문에 Pageable 사용 불가
	
	2. 1:N 관계가 두 개 이상인 경우 사용 불가
	
###2. Entity Graph
	
	- 엔티티그래프를 네이밍을 이용해서 읽어와야하는 필드를 지정하고
	
	- JPQL 작성 시에 해당 이름을 이용해서 설정해서 실행한다.
	
	- Fetch Join과 같은 실행 방식을 가지고 있지만 네이밍을 이용해서 재사용이 가능하다는 장점이 있다.
	
	- 엔티티 테이블이 연속적으로 연관 관계를 가지는 경우 subgraph를 이용해서 계속 설정이 가능하다는 점도 있다.
	
	@NamedEntityGraph(
	    name = "Member.boardList",   // 그래프 이름 지정
	    attributeNodes = @NamedAttributeNode("boardList") // 읽어와야 하는 속성 지정
	)
	@Entity
	public class Member { ... }
	
	// 그래프 설정들 중 "Member.boardList"로 정의된 속성들을 읽어오는 질의를 실행
	@EntityGraph(value = "Member.boardList", type = EntityGraphType.LOAD)
	@Query("select m from Member m where birthYear < :1")
	List<Member> getMemberWithBoardList(int birthYear);
	
###3. Batch Size
	
	- application.properties에 설정을 하거나 @BatchSize를 이용해서 엔티티에 직접 설정할 수 있다.
	
		spring.jpa.properties.hibernate.default_batch_fetch_size=10

	@Entity
	public class Member {
	
	     ~ 생략 ~
	
	    @BatchSize(size = 10)
	    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	    private List<Board> boardList;
	}

	- 아래 질의 결과 100명의 Member 엔티티가 검색되었다고 가정하면
	
	@Query("select m from Member m where birthYear < :1")
	List<Member> getMemberByBirthYear(int birthYear);

	- 위 설정과 같이 10개씩 배치로 처리하도록 설정해 두었을 경우라면, 각 Member 엔티티에 연관된 Board 엔티티를 검색하기 위한 질의가 아래와 같이 실행된다.
	
	select b.id, b.title, b.content, b.username
	from Board b
	where b.username IN ("m1", "m2", "m3", "m4", "m5", "m6", "m7", "m8", "m9", "m10");
	
	- 이때 m1 ~ m10은 위의 질의에서 검색된 Member 엔티티의 username이 입력된다.
	- 결과적으로 Member 엔티티 각각에 대해 총 100개의 질의가 실행되어야 하지만, 배치 사이즈인 10개씩 묶어서 총 10개의 추가적인 질의만 실행된다.
