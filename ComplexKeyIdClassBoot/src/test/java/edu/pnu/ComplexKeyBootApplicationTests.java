package edu.pnu;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import edu.pnu.domain.Club;
import edu.pnu.domain.Member;
import edu.pnu.domain.MemberClub;
import edu.pnu.persistence.ClubRepository;
import edu.pnu.persistence.MemberClubRepository;
import edu.pnu.persistence.MemberRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ComplexKeyBootApplicationTests {

	@Autowired private MemberRepository memRepo;
	@Autowired private ClubRepository clubRepo;
	@Autowired private MemberClubRepository relRepo;
	
	@Test
	@Order(1)
	public void insertData() {

		Club club1 = Club.builder().clubName("축구").build();
		Club club2 = Club.builder().clubName("야구").build();
		club1 = clubRepo.save(club1);
		club2 = clubRepo.save(club2);

		Member m1 = Member.builder().username("m1").password("1234").build();
		Member m2 = Member.builder().username("m2").password("2345").build();
		m1 = memRepo.save(m1);
		m2 = memRepo.save(m2);
		
		MemberClub r1 = new MemberClub(m1, club1);
		MemberClub r2 = new MemberClub(m2, club2);
		relRepo.save(r1);
		relRepo.save(r2);

		System.out.println("-".repeat(80));
	}
	
	@Test
	@Transactional
	@Order(2)
	public void getAllMember() {
		List<Member> list = memRepo.findAll();
		for (Member m : list)
			System.out.println(m);
		
		System.out.println("-".repeat(80));
	}

	@Test
	@Transactional
	@Order(3)
	public void getAllClub() {
		List<Club> list = clubRepo.findAll();
		for (Club c : list)
			System.out.println(c);
		
		System.out.println("-".repeat(80));
	}
	
	@Test
	@Transactional
	@Order(4)
	public void getAllMemberClub() {
		List<MemberClub> list = relRepo.findAll();
		for (MemberClub r : list)
			System.out.println(r);
		
		System.out.println("-".repeat(80));
	}

	@Test
	@Transactional
	@Rollback(false)
	@Order(5)
	public void test01() {
		System.out.println("m1의 축구 연결을 삭제");
		
		MemberClub rel = relRepo.findByMember_UsernameAndClub_ClubName("m1", "축구");
		System.out.println("삭제 대상 : " + rel);
		relRepo.delete(rel);
		
		System.out.println("-".repeat(80));
		getAllMemberClub();
	}

	@Test
	@Transactional
	@Rollback(false)
	@Order(6)
	public void test02() {
		System.out.println("m1 삭제");
		
		Member m = memRepo.findById("m1").get();
		memRepo.delete(m);
		
		System.out.println("-".repeat(80));
		getAllMemberClub();
	}
}