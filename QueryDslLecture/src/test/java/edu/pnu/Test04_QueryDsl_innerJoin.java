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

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import edu.pnu.domain.Board;
import edu.pnu.domain.Member;
import edu.pnu.domain.QBoard;
import edu.pnu.domain.QMember;
import edu.pnu.persistence.BoardRepository;
import edu.pnu.persistence.MemberRepository;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
class Test04_QueryDsl_innerJoin {

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
	
	@DisplayName("using innerJoin Test")
	@Test
	@Order(2)
	void selectFromFetchOneTest() {
		QBoard board = QBoard.board;
		QMember member = QMember.member;
		
		List<Tuple> list = queryFactory.select(board.seq, board.title, board.visitCount, member.nickname)
									    .from(board)
									    .innerJoin(board.member, member)
									    .where(board.visitCount.lt(50))
									    .fetch();
		for (Tuple tp : list) {
			Object[] arr = tp.toArray();
			for (int i = 0 ; i < arr.length ; i++) {
				if (0 < i) System.out.print(",");
				System.out.print(arr[i]);
			}
			System.out.println();
		}
	}
}