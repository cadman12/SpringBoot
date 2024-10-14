package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import edu.pnu.domain.ActorInfo;

public interface ActorInfoRepository extends JpaRepository<ActorInfo, Integer> {

	List<ActorInfo> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);

	@Procedure("film_in_stock1")
	List<Object[]> filmInStock1();
//  입력, 출력 파라미터가 없고 질의만 호출하는 경우
//	CREATE DEFINER=`root`@`localhost` PROCEDURE `film_in_stock1`()
//	    READS SQL DATA
//	BEGIN
//	     SELECT inventory_id, film_id, store_id
//	     FROM inventory
//	     WHERE film_id = 1;
//	END

	@Procedure("film_in_stock2")
	List<Object[]> filmInStock2(Integer p_film_id, Integer p_store_id);
//  1개 이상의 입력 파라미터가 있고, 출력 파라미터 없이 호출하는 경우
//	CREATE DEFINER=`root`@`localhost` PROCEDURE `film_in_stock1`(IN p_film_id INT, IN p_store_id INT)
//		READS SQL DATA
//	BEGIN
//		SELECT inventory_id, film_id, store_id
//		FROM inventory
//		WHERE film_id = p_film_id
//		AND store_id = p_store_id
//		AND inventory_in_stock(inventory_id);
//	END

	@Procedure("film_in_stock3")
	Integer filmInStock3(Integer p_film_id, Integer p_store_id);
//  1개 이상 input 파라미터, 1개의 output 파라미터 ==> output 파라미터는 메서드의 리턴값으로 변환된다.
//	CREATE DEFINER=`root`@`localhost` PROCEDURE `film_in_stock1`(IN p_film_id INT, IN p_store_id INT, OUT p_film_count INT)
//		READS SQL DATA
//	BEGIN
//		SELECT COUNT(*)
//		FROM inventory
//		WHERE film_id = p_film_id
//		AND store_id = p_store_id
//		AND inventory_in_stock(inventory_id)
//		INTO p_film_count;
//	END

//	@Procedure("film_in_stock4")
//	List<Object> filmInStock4(Integer p_film_id, Integer p_store_id);
//  1개 이상 input 파라미터, 2개 이상 output 파라미터 ==> JPA에서는 복수의 output 파라미터는 지원하지 않음.
//	==> StoredProcedureQuery 객체를 이용해서 처리함. 
//	CREATE DEFINER=`root`@`localhost` PROCEDURE `film_in_stock4`(IN p_film_id INT, IN p_store_id INT, OUT p_film_count INT, OUT sum INT)
//	    READS SQL DATA
//	BEGIN
//	     SELECT COUNT(*)
//	     FROM inventory
//	     WHERE film_id = p_film_id
//	     AND store_id = p_store_id
//	     AND inventory_in_stock(inventory_id)
//	     INTO p_film_count;
//		     
//	     SELECT sum(inventory_id)
//	     FROM inventory
//	     WHERE film_id = p_film_id
//	     AND store_id = p_store_id
//	     AND inventory_in_stock(inventory_id)
//	     INTO sum;     
//	END

//	@Procedure("film_in_stock")
//	List<Object> filmInStock(Integer p_film_id, Integer p_store_id);
//  1개 이상 input 파라미터, 1개 이상 output 파라미터와 질의 결과 셋이 있는 경우
//	==> 복수개의 질의가 연속될 경우 제일 첫번째 질의의 결과셋이 리턴된다.
//	==> JPA에서는 복수의 output 파라미터는 지원하지 않음.
//	==> StoredProcedureQuery 객체를 이용해서 처리함. 
//	CREATE DEFINER=`root`@`localhost` PROCEDURE `film_in_stock`(IN p_film_id INT, IN p_store_id INT, OUT p_film_count INT)
//	    READS SQL DATA
//	BEGIN
//	    SELECT inventory_id
//	    FROM inventory
//	    WHERE film_id = p_film_id
//	    AND store_id = p_store_id
//	    AND inventory_in_stock(inventory_id);
//	
//	    SELECT COUNT(*)
//	    FROM inventory
//	    WHERE film_id = p_film_id
//	    AND store_id = p_store_id
//	    AND inventory_in_stock(inventory_id)
//	    INTO p_film_count;	     
//	END	
}
