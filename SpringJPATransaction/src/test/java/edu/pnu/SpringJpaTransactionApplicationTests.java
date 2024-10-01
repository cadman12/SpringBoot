package edu.pnu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
class SpringJpaTransactionApplicationTests {

	@Autowired
	private MemberRepository memberRepository;
	
	@Test
	void contextLoads() {
		
		memberRepository.save(new Member("a", "b", "c", true));
		
	}

}
