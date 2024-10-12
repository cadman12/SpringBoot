package edu.pnu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

@SpringBootTest
class StoredProcedureTest {

	@Autowired
	private EntityManager entityManager;
	
	@Test
	void test01() {
		// 저장 프로시저 실행 객체 생성
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("film_in_stock");
		
		// 프로시저 파라미터 등록
		query.registerStoredProcedureParameter("p_film_id", Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("p_store_id", Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("p_film_count", Integer.class, ParameterMode.OUT);
		
		// 입력값 설정
		query.setParameter("p_film_id", 1);
		query.setParameter("p_store_id", 1);
		
		// 저장 프로시저 실행
		query.execute();
		
		// 결과값 추출
		System.out.println(query.getOutputParameterValue("p_film_count"));
	}
}
