# View & Stored Procedure 사용 예제

## 1.테스트용 데이터베이스

	- MySQL 기본 예제 데이터베이스 sakila


### 2.View

	- Entity Class : ActorInfo
	- Repository : ActorInfoRepository

	- 실행 : ViewTest - test01, test02


### 3.Stored Procedure

	- 실행 : StoredProcedureTest - test01
	
	- https://docs.spring.io/spring-data/jpa/reference/jpa/stored-procedures.html
	
	- ActorInfoRepository.java
	
		. test01 : StoredProcedureQuery 객체를 이용한 저장 프로시저 실행 예제 코드
		
		. test02 : @Procedure 를 이용한 저장 프로시저 실행 예제 코드 1

			// 예제 film_in_stock에서 output 파라미터를 넘기는 질의만 남긴 새로운 저장 프로시저를 생성해서 테스트
			@Procedure("film_in_stock1")
			Integer filmInStock1(Integer p_film_id, Integer p_store_id);

			CREATE DEFINER=`root`@`localhost` PROCEDURE `film_in_stock1`(IN p_film_id INT, IN p_store_id INT, OUT p_film_count INT) READS SQL DATA
			BEGIN
			     SELECT COUNT(*)
			     FROM inventory
			     WHERE film_id = p_film_id
			     AND store_id = p_store_id
			     AND inventory_in_stock(inventory_id)
			     INTO p_film_count;
			END
			
		. test03 : @Procedure 를 이용한 저장 프로시저 실행 예제 코드 2
		
			// 예제 film_in_stock에서 output 파라미터를 제거하고 질의만 남긴 새로운 저장 프로시저를 생성해서 테스트
			@Procedure("film_in_stock2")
			List<Object[]> filmInStock2(Integer p_film_id, Integer p_store_id);

			CREATE DEFINER=`root`@`localhost` PROCEDURE `film_in_stock2`(IN p_film_id INT, IN p_store_id INT) READS SQL DATA
			BEGIN
			     SELECT inventory_id
			     FROM inventory
			     WHERE film_id = p_film_id
			     AND store_id = p_store_id
			     AND inventory_in_stock(inventory_id);
     
			     SELECT *
			     FROM actor;
			END
			
			==> 질의가 1개 이상인 경우에는 제일 처음 질의 결과만 리턴

	- 질의 결과와 output 파라미터에 값을 설정하는 두가지 방법을 모두 사용하는 방법은 테스트 확인 후 추가 예정
