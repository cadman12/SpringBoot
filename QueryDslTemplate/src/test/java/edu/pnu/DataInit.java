package edu.pnu;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Board;
import edu.pnu.domain.Member;
import edu.pnu.persistence.BoardRepository;
import edu.pnu.persistence.MemberRepository;

@SpringBootTest
public class DataInit {

	@Autowired
	private BoardRepository boardRepo;
	@Autowired
	private MemberRepository memberRepo;
	
	@Test
	public void datainit() {
		
		Member scott = Member.builder().username("scott").password("tiger").role("ROLE_USER").enabled(true).build();	
		Member tom = Member.builder().username("tom").password("tiger").role("ROLE_USER").enabled(true).build();	
		memberRepo.save(scott);
		memberRepo.save(tom);

		for (int i = 1 ; i <= 20 ; i++) {
			boardRepo.save(Board.builder().title("title_scott" + i).content("content_scott" + i)
					.date(new Date()).member(scott).build());
		}
		for (int i = 1 ; i <= 20 ; i++) {
			boardRepo.save(Board.builder().title("title_tom" + i).content("content_tom" + i)
					.date(new Date()).member(tom).build());
		}
	}
}
