package edu.pnu;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@SpringBootTest
public class JPADynamicExamApplicationTests {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Test
	public void testDynamicQuery() {
		String searchCondition = "TITLE";
		String searchKeyword = "title1";

		StringBuilder sb = new StringBuilder("select b from Board b where 1=1");

		if (searchCondition.equals("TITLE")) {
			sb.append(" AND b.title like '%" + searchKeyword + "%'");
		} else if (searchCondition.equals("CONTENT")) {
			sb.append(" AND b.content like '%" + searchKeyword + "%'");
		}
		sb.append(" ORDER BY b.id ASC");
		
		TypedQuery<Board> query = entityManager.createQuery(sb.toString(), Board.class);
		query.setFirstResult(5);
        query.setMaxResults(5);
		
		List<Board> list = query.getResultList();
		for(Board b : list) {
			System.out.println(b.toString());
		}
	}
}
