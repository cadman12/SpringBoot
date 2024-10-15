# View & Stored Procedure 사용 예제

## 1.테스트용 데이터베이스

- MySQL 기본 예제 데이터베이스 sakila


### 2.View

- Entity Class : ActorInfo

- Repository : ActorInfoRepository

- 실행 : ViewTest - test01, test02


### 3.Stored Procedure

- Stored Procedure : film_in_stock

- 실행 : StoredProcedureTest.java
	
- https://docs.spring.io/spring-data/jpa/reference/jpa/stored-procedures.html


= ActorInfoRepository.java

1)입력, 출력 파라미터가 없고 질의만 호출하는 경우

	@Procedure("film_in_stock1")
	List<Object[]> filmInStock1();

	CREATE DEFINER=`root`@`localhost` PROCEDURE `film_in_stock1`()
	    READS SQL DATA
	BEGIN
	     SELECT inventory_id, film_id, store_id
	     FROM inventory
	     WHERE film_id = 1;
	END
		
2)1개 이상의 입력 파라미터가 있고, 출력 파라미터 없이 호출하는 경우

	@Procedure("film_in_stock2")
	List<Object[]> filmInStock2(Integer p_film_id, Integer p_store_id);

	CREATE DEFINER=`root`@`localhost` PROCEDURE `film_in_stock2`(IN p_film_id INT, IN p_store_id INT)
		READS SQL DATA
	BEGIN
		SELECT inventory_id, film_id, store_id
		FROM inventory
		WHERE film_id = p_film_id
		AND store_id = p_store_id
		AND inventory_in_stock(inventory_id);
	END

3)1개 이상 input 파라미터, 1개의 output 파라미터 ==> output 파라미터는 메서드의 리턴값으로 변환된다.

	@Procedure("film_in_stock3")
	Integer filmInStock3(Integer p_film_id, Integer p_store_id);
		
	CREATE DEFINER=`root`@`localhost` PROCEDURE `film_in_stock3`(IN p_film_id INT, IN p_store_id INT, OUT p_film_count INT)
		READS SQL DATA
	BEGIN
		SELECT COUNT(*)
		FROM inventory
		WHERE film_id = p_film_id
		AND store_id = p_store_id
		AND inventory_in_stock(inventory_id)
		INTO p_film_count;
	END
		
4)1개 이상 input 파라미터, 2개 이상 output 파라미터

  	. JPA에서는 복수의 output 파라미터는 지원하지 않음.
	. StoredProcedureQuery 객체를 이용해서 처리함.
		
	CREATE DEFINER=`root`@`localhost` PROCEDURE `film_in_stock4`(IN p_film_id INT, IN p_store_id INT, OUT p_film_count INT, OUT sum INT)
	    READS SQL DATA
	BEGIN
	     SELECT COUNT(*)
	     FROM inventory
	     WHERE film_id = p_film_id
	     AND store_id = p_store_id
	     AND inventory_in_stock(inventory_id)
	     INTO p_film_count;
			     
	     SELECT sum(inventory_id)
	     FROM inventory
	     WHERE film_id = p_film_id
	     AND store_id = p_store_id
	     AND inventory_in_stock(inventory_id)
	     INTO sum;     
	END

5)1개 이상 input 파라미터, 1개 이상 output 파라미터와 질의 결과 셋이 있는 경우

	. JPA에서는 복수의 output 파라미터는 지원하지 않음.
	. StoredProcedureQuery 객체를 이용해서 처리함. 

	CREATE DEFINER=`root`@`localhost` PROCEDURE `film_in_stock`(IN p_film_id INT, IN p_store_id INT, OUT p_film_count INT)
	    READS SQL DATA
	BEGIN
	    SELECT inventory_id
	    FROM inventory
	    WHERE film_id = p_film_id
	    AND store_id = p_store_id
	    AND inventory_in_stock(inventory_id);
		
	    SELECT COUNT(*)
	    FROM inventory
	    WHERE film_id = p_film_id
	    AND store_id = p_store_id
	    AND inventory_in_stock(inventory_id)
	    INTO p_film_count;	     
	END	
