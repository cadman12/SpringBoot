package edu.pnu;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.ActorInfo;
import edu.pnu.persistence.ActorInfoRepository;

@SpringBootTest
class ViewTest {

	@Autowired
	private ActorInfoRepository aiRepo;
	
	@Test
	void test01() {
		// View의 모든 데이터 검색
		List<ActorInfo> list = aiRepo.findAll();
		
		for(ActorInfo ai : list) {
			System.out.println(ai.toString());
		}
	}

	@Test
	void test02() {
		// firstName 또는 lastName에 PE가 포함된 데이터 검색
		List<ActorInfo> list = aiRepo.findByFirstNameContainingOrLastNameContaining("PE", "PE");
		for(ActorInfo ai : list) {
			System.out.println(ai.toString());
		}
	}
}
