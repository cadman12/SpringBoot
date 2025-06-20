package edu.pnu;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.h2.entity.DualTestH2;
import edu.pnu.h2.persistence.DualTestH2Repository;
import edu.pnu.mysql.entity.DualTestMySQL;
import edu.pnu.mysql.persistence.DualTestMysqlRepository;

@SpringBootTest
class DualDbBootApplicationTests {
//
//	@Autowired
//	DualTestMysqlRepository mysqlRepo;
//
//	@Autowired
//	DualTestH2Repository h2Repo;
//	
	
	@Test
	void insertData() {
		System.out.println("insertData");
//		h2Repo.save(DualTestH2.builder().title("t").content("c").date(new Date()).build());
//		mysqlRepo.save(DualTestMySQL.builder().title("t").content("c").date(new Date()).build());
		
	}

}
