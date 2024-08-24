package edu.pnu;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import edu.pnu.domain.Testtbl;
import edu.pnu.persistence.TesttblRepository;

@SpringBootTest
class StoredProcedureBootApplicationTests {

	@Autowired
	TesttblRepository repo;
	
	// 저장 프로시저를 호출하는 메소드에서는 Transactional로 지정해줘야 함.
	@Transactional
	@Test
	void contextLoads() {
		System.out.println("contextLoads in");
		
		try {
			List<Testtbl> list = repo.searchSchedule(2024, 2, 20);
			
			if (list != null) {
				list.forEach(t -> System.out.println(t));	
			} else {
				System.out.println("list is null");
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
