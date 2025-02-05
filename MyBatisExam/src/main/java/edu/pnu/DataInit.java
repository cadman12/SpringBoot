package edu.pnu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import edu.pnu.domain.Board;
import edu.pnu.persistence.mapper.BoardMapper;

@Component
public class DataInit implements ApplicationRunner {

	@Autowired	
    private BoardMapper bulletinMapper;	
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		for(int i = 1 ; i <= 100 ; i++) {
			
			Board b = new Board();
			b.setAuthor("홍길동");
			b.setTitle("title" + i);
			b.setContent("content" + i);
			
			bulletinMapper.insertBoard(b);
		}
	}

}
