package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import edu.pnu.domain.ActorInfo;

public interface ActorInfoRepository extends JpaRepository<ActorInfo, Integer> {

	List<ActorInfo> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
	
	@Procedure("film_in_stock1")
	Integer filmInStock1(Integer p_film_id, Integer p_store_id);
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
	
	@Procedure("film_in_stock2")
	List<Object[]> filmInStock2(Integer p_film_id, Integer p_store_id);
//	CREATE DEFINER=`root`@`localhost` PROCEDURE `film_in_stock2`(IN p_film_id INT, IN p_store_id INT)
//		READS SQL DATA
//	BEGIN
//	    SELECT inventory_id
//		FROM inventory
//		WHERE film_id = p_film_id
//		AND store_id = p_store_id
//		AND inventory_in_stock(inventory_id);
//		     
//		SELECT *
//		FROM actor;
//	END
}
