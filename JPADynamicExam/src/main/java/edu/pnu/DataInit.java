package edu.pnu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import edu.pnu.domain.Board;
import edu.pnu.persistence.BoardRepository;

@Component
public class DataInit implements ApplicationRunner {

	@Autowired	
    private BoardRepository boardRepo;	
	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		for(int i = 1 ; i <= 50 ; i++) {
			Board b = new Board();
			b.setTitle("title" + i);
			b.setContent("content" + i);
			b.setAuthor("author" + (i%5==0?5:i%5));
			boardRepo.save(b);
			
			for(int j = 1 ; j <= 3 ; j++) {
				Board r = new Board();
				r.setTitle("title" + i + "." + j);
				r.setContent("reply" + i + "." + j);
				r.setAuthor("author" + i + "." + j);
				r.setBoard(b);
				boardRepo.save(r);
			}
		}		
	}
}
