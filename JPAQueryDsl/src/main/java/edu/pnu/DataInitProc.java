package edu.pnu;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import edu.pnu.domain.Board;
import edu.pnu.persistence.BoardRepository;

@Component
public class DataInitProc implements ApplicationRunner {

	@Autowired
	private BoardRepository boardRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		for (int i = 1 ; i <= 100 ; i++) {
			boardRepo.save(Board.builder()
					.title("title" + i)
					.writer("관리자")
					.content("content" + i)
					.createDate(new Date())
					.cnt((long)(Math.random()*100))
					.build());
		}
	}
}
