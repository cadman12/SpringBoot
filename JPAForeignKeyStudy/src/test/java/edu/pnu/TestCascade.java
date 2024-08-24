package edu.pnu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.persistence.MemberRepository;

@SpringBootTest
public class TestCascade {

	@Autowired
	MemberRepository memberRepo;
	
	@Test
	public void test01() {
		memberRepo.deleteById("a");
	}
}
