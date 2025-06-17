package edu.pnu;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Club;
import edu.pnu.domain.Member;
import edu.pnu.domain.MemberClub;
import edu.pnu.persistence.ClubRepository;
import edu.pnu.persistence.MemberClubRepository;
import edu.pnu.persistence.MemberRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ComplexKeyTestsIdClass {

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

		Member m1 = Member.builder().username("m1").password("p1").build();
		Member m2 = Member.builder().username("m2").password("p2").build();
		m1 = memRepo.save(m1);
		m2 = memRepo.save(m2);
		
		MemberClub r1 = new MemberClub(m1, club1);
		MemberClub r2 = new MemberClub(m2, club1);
		MemberClub r3 = new MemberClub(m2, club2);
		relRepo.save(r1);
		relRepo.save(r2);
		relRepo.save(r3);

		System.out.println("-".repeat(80));
	}
	
	@Test
	@Order(2)
	public void getAllMember() {
		List<Member> list = memRepo.findAll();
		for (Member m : list)
			System.out.println(m);
		
		System.out.println("-".repeat(80));
	}

	@Test
	@Order(3)
	public void getAllClub() {
		List<Club> list = clubRepo.findAll();
		for (Club c : list)
			System.out.println(c);
		
		System.out.println("-".repeat(80));
	}
	
	@Test
	@Order(4)
	public void getAllMemberClub() {
		List<MemberClub> list = relRepo.findAll();
		for (MemberClub r : list)
			System.out.println(r);
		
		System.out.println("-".repeat(80));
	}

	@Test
	@Order(5)
	public void getMemberClub() {
		List<MemberClub> list = relRepo.findByClub_ClubName("축구");
		for (MemberClub r : list)
			System.out.println(r);
		
		System.out.println("-".repeat(80));
	}
}
