package edu.pnu;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import edu.pnu.entity.Member;
import edu.pnu.persistence.MemberRepository;

@Component
public class DataInit implements ApplicationRunner {

	@Autowired
	private MemberRepository memRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		memRepo.save(Member.builder().username("member").password("1234").role("ROLE_MEMBER").joinDate(new Date()).build());
		memRepo.save(Member.builder().username("manager").password("1234").role("ROLE_MANAGER").joinDate(new Date()).build());
		memRepo.save(Member.builder().username("admin").password("1234").role("ROLE_ADMIN").joinDate(new Date()).build());
	}
}
