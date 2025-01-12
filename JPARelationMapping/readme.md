# JPA 연관관계 설정 예제 코드

	- ManyToOne
	
	- OneToOne

	- ManyToMany

# 테스트 데이터 입력 (edu.pnu.DataInit.java)

  - M2ODataInit : ManyToOne 테스트용 데이터 입력 메서드

  - O2ODataInit : OneToOne 테스트용 데이터 입력 메서드

  - M2MDataInit1 : ManyToMany 테스트용 데이터 입력 메서드 (Self Relation)

  - M2MDataInit2 : ManyToMany 테스트용 데이터 입력 메서드

# 테스트 URL

	- @GetMapping("/m2o") : ManyToOne 연관관계 데이터 테스트 URL
 
	- @GetMapping("/o2o") : OneToOne 연관관계 데이터 테스트 URL
 
	- @GetMapping("/m2m") : ManyToMany 연관관계 데이터 테스트 URL (Member)
 
	- @GetMapping("/m2mt1") : ManyToMany 연관관계 데이터 테스트 URL (Table1)
 
	- @GetMapping("/m2mt2") : ManyToMany 연관관계 데이터 테스트 URL (Table2)
 
