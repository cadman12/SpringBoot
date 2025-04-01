package edu.pnu.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {

	// Propagation.REQUIRED (default)
	//		TestController.join과 TestService.join이 하나의 트랜잭션으로 묶인다.
	//		TestService.join이 실패하면 TestController.join도 같이 rollback
	// Propagation.REQUIRES_NEW
	//		TestController.join의 트랜잭션과 별개로 TestService.join 트랜잭션이 생성.
	//		TestService.join이 실패여부와 상관없이 TestController.join은 실행
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean join() {
		
		// Business Process
		
		return true;
	}
}
