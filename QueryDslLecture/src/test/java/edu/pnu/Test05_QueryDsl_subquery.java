package edu.pnu;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import edu.pnu.domain.Board;
import edu.pnu.domain.Member;
import edu.pnu.domain.QBoard;
import edu.pnu.persistence.BoardRepository;
import edu.pnu.persistence.MemberRepository;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
class Test05_QueryDsl_subquery {

	@Autowired
	private MemberRepository memberRepo;

	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private JPAQueryFactory queryFactory;

	@DisplayName("data Insert")
	@Test
	@Order(1)
	void dataInit() {
		Member member1 = Member.builder().username("username1").password("abcd").nickname("홍길동").role("ROLE_USER").enabled(true).build();
		memberRepo.save(member1);
		Member member2 = Member.builder().username("username2").password("abcd").nickname("이순신").role("ROLE_USER").enabled(true).build();
		memberRepo.save(member2);

		Random rd = new Random();
		for (int i = 1 ; i <= 10 ; i++) {
			Member member = (i%2 == 1)?member1:member2;
			Board board = Board.builder().title("title"+i).content("content"+i).visitCount(rd.nextLong(100)).member(member).build();
			boardRepo.save(board);
		}
	}
	
	@DisplayName("using subQuery Test")
	@Test
	@Order(2)
	void subQueryTest() {
	
		System.out.println(">>>> subQueryTest");

		QBoard board = QBoard.board;
		
		System.out.println("avg : " + queryFactory.select(board.visitCount.avg()).from(board).fetchOne());
		
		List<Board> list = queryFactory.selectFrom(board)
			    .where(board.visitCount.gt((JPAExpressions.select(board.visitCount.avg()).from(board))))
			    .fetch();

		for (Board tp : list) {
			System.out.println(tp);
		}
	}
}




