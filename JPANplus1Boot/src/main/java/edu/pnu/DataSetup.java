package edu.pnu;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import edu.pnu.domain.Board;
import edu.pnu.domain.Member;
import edu.pnu.repository.BoardRepository;
import edu.pnu.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DataSetup implements ApplicationRunner {

	private final BoardRepository boardRepo;
	private final MemberRepository memberRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		// 데이터입력
		for (int i = 1 ; i <= 5 ; i++) {

			Member m = Member.builder().username("m" + i).password("p" + i).build();
			memberRepo.save(m);
	
			for (int j = 1 ; j <= 10 ; j++) {
				Board b = Board.builder().title("t2_" + j).content("c2_" + j).member(m).build();
				boardRepo.save(b);
			}
		}
	}
}
