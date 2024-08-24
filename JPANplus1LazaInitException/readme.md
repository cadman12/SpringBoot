# N+1 문제

## 문제 내용

	- 어떤 질의가 있을 때 RDB인 경우에는 대부분의 경우가 SQL문 하나로 해결이 가능

	- JPA에서는 연관관계를 맺고 있는 엔티티 Member/Board가 있고, Board를 검색했을 때 Board 내의 참조변수 Member를 터치하는 순간 다시 SQL문을 생성해서 질의하는 문제가 발생한다.

	- 이때 한번의 질의로 생성된 Board 객체 리스트에 대해 각각의 Board에 대해 Member 객체를 읽어오기 위한 새로운 질의들이 추가적으로 발생하는 문제를 N+1 문제라고 한다.

	- fetchType이 eager든 lazy든 결국은 같은 문제가 발생한다. 하지만 Member 객체를 읽을 필요가 없는 경우에는 굳이 eager 방식을 이용할 필요는 없다.

## 해결 방법

### 1. Fetch Join

	- JPQL의 join fetch를 이용하는 방법. eager와 동일하게 질의 당시에 모든 데이터를 읽어오게 됨.

	- 제일 쉬운 해결 방법으로 판단됨. 하지만 리포지토리 메소드가 필요 이상으로 증가하게 되는 단점도 있음.

	@Query("select b from Board b join fetch b.member")
	List<Board> boardTest01();

	@Query("select distinct b from Board b left join fetch b.member where b.id<10 order by id asc")
	List<Board> boardTest02();
	
	@Query("select distinct p from Project p left join fetch p.details where display=true order by id desc")
	List<Project> findAllToDisplay();

	
### 2. @BatchSize

	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
	@Builder.Default
	@BatchSize(size = 20)
	Set<Board> set = new HashSet<>();

