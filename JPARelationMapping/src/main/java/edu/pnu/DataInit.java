package edu.pnu;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import edu.pnu.domain.M2MMember;
import edu.pnu.domain.M2MTable1;
import edu.pnu.domain.M2MTable2;
import edu.pnu.domain.M2OBoard;
import edu.pnu.domain.M2OMember;
import edu.pnu.domain.O2OMember;
import edu.pnu.domain.O2OMemberInfo;
import edu.pnu.persistence.M2MMemberRepository;
import edu.pnu.persistence.M2MTable1Repository;
import edu.pnu.persistence.M2OMemberRepository;
import edu.pnu.persistence.O2OMemberRepository;

@Component
public class DataInit implements ApplicationRunner {

	@Autowired private M2OMemberRepository m2oMRepo;
	@Autowired private O2OMemberRepository o2oMRepo;
	@Autowired private M2MMemberRepository m2mMRepo;
	@Autowired private M2MTable1Repository m2mT1Repo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		M2ODataInit();
		O2ODataInit();
		M2MDataInit1();
		M2MDataInit2();
	}
	
	private void M2ODataInit() {
		M2OMember m1 = new M2OMember();
		m1.setUsername("user1");
		M2OMember m2 = new M2OMember();
		m2.setUsername("user2");

		for(int i = 1 ; i <= 2 ; i++) {
			M2OMember m = (i == 1)?m1:m2;
			for(int j = 1 ; j <= 3 ; j++) {
				M2OBoard b = new M2OBoard();
				b.setTitle("title" + i + j);
				b.setContent("content" + i + j);
				b.setCreateData(new Date());
				b.setVisit(0L);
				b.setMember(m);
			}
			m2oMRepo.save(m);
		}
	}
	
	private void O2ODataInit() {
		
		O2OMember m = new O2OMember();
		m.setUsername("user");
		m.setPassword("1234");
		m.setRole("ROLE_USER");
		m.setEnabled(true);

		O2OMemberInfo mi = new O2OMemberInfo();
		mi.setUsername("user");
		mi.setAddress("addr");
		mi.setBirth(new Date());
		mi.setGender("Man");

		m.setMemberinfo(mi);
		
		// cascade 설정때문에 O2OMember만 저장해도 O2OMemberInfo도 같이 저장됨.
		// 만약 O2OMemberInfo 객체를 만들지 않고 O2OMember를 저장하면 O2OMember만 저장됨.
		// orphanRemoval 옵션은 삭제인 경우 연관된 엔티티가 존재한다면 같이 삭제되는 설정임.
		o2oMRepo.save(m);		
	}
	
	private void M2MDataInit1() {
		M2MMember m1 = new M2MMember();
		m1.setUsername("user1");
		M2MMember m2 = new M2MMember();
		m2.setUsername("user2");
		M2MMember m3 = new M2MMember();
		m3.setUsername("user3");

		m1.setFollowing(m2);
		m1.setFollowing(m3);
		m2.setFollowing(m1);
		m3.setFollowing(m2);

		m2mMRepo.save(m1);		
		m2mMRepo.save(m2);		
		m2mMRepo.save(m3);		
	}
	
	private void M2MDataInit2() {
		M2MTable1 t1 = new M2MTable1();
		t1.setUsername("user1");
		t1.setVal1("val1");
		M2MTable1 t2 = new M2MTable1();
		t2.setUsername("user2");
		t2.setVal1("val2");
		M2MTable2 t3 = new M2MTable2();
		t3.setUsername("user3");
		t3.setVal2("val3");
		M2MTable2 t4 = new M2MTable2();
		t4.setUsername("user4");
		t4.setVal2("val4");
		
		t1.setTable2set(t3);
		t1.setTable2set(t4);
		t2.setTable2set(t3);
		
		m2mT1Repo.save(t1);
		m2mT1Repo.save(t2);
	}
}
