package edu.pnu;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.persistence.ActorInfoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
class StoredProcedureTest {

	@Autowired
	private ActorInfoRepository aiRepo;
	
	@Autowired
	private EntityManager entityManager;

	@Test
	void test01() {
		
		System.out.println("test01:입력, 출력 파라미터가 없고 질의만 호출하는 경우");

		List<Object[]> res = aiRepo.filmInStock1();
		for (Object[] obj : res) {
			for (Object o : obj) {
				System.out.print(o.toString() + ", ");
			}
			System.out.println();
		}
	}	
	
	@Test
	void test02() {
		
		System.out.println("test02:1개 이상의 입력 파라미터가 있고, 출력 파라미터 없이 호출하는 경우");

		List<Object[]> list = aiRepo.filmInStock2(1, 1);

		for (Object[] objs : list) {
			System.out.println(objs);
		}
	}
	
	@Test
	void test03() {
		
		System.out.println("test03:1개 이상 input 파라미터, 1개의 output 파라미터 ==> output 파라미터는 메서드의 리턴값으로 변환된다.");

		int res = aiRepo.filmInStock3(1, 1);
		System.out.println(res);
	}
	
	@Test
	void test04() {
		
		System.out.println("test04:1개 이상 input 파라미터, 2개 이상 output 파라미터");

		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("film_in_stock4");
        
        // IN 매개변수 설정
        query.registerStoredProcedureParameter("p_film_id", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_store_id", Integer.class, ParameterMode.IN);
        
        // OUT 매개변수 설정
        query.registerStoredProcedureParameter("p_film_count", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("sum", Integer.class, ParameterMode.OUT);
        
        // 매개변수 값 설정
        query.setParameter("p_film_id", 1);
        query.setParameter("p_store_id", 1);

        // 실행
        query.execute();

        // OUT 매개변수 값 가져오기
        Integer p_film_count = (Integer) query.getOutputParameterValue("p_film_count");
        Integer sum = (Integer) query.getOutputParameterValue("sum");

        System.out.println("p_film_count:" + p_film_count + ", sum:" + sum);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void test05() {
		
		System.out.println("test05:1개 이상 input 파라미터, 1개 이상 output 파라미터, 질의 결과 셋");

		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("film_in_stock");
        
        // IN 매개변수 설정
        query.registerStoredProcedureParameter("p_film_id", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_store_id", Integer.class, ParameterMode.IN);
        
        // OUT 매개변수 설정
        query.registerStoredProcedureParameter("p_film_count", Integer.class, ParameterMode.OUT);
        
        // 매개변수 값 설정
        query.setParameter("p_film_id", 1);
        query.setParameter("p_store_id", 1);

        // 실행
        boolean hasResultSet = query.execute();

        // OUT 매개변수 값 가져오기
        Integer p_film_count = (Integer) query.getOutputParameterValue("p_film_count");

        System.out.println("p_film_count:" + p_film_count);
        
        if (hasResultSet) {
        	// 질의 결과 셋이 여러 필드인 경우라면 List<Object>가 아닌 List<Object[]>
			List<Object> list = query.getResultList();
    		for (Object obj : list) {
    			System.out.println(obj);
    		}
    		
    		while(query.hasMoreResults()) {
    			list = query.getResultList();
        		for (Object obj : list) {
        			System.out.println(obj);
        		}
            }
        }
	}			
}
