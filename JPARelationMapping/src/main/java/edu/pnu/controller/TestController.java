package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.M2MMember;
import edu.pnu.domain.M2MTable1;
import edu.pnu.domain.M2MTable2;
import edu.pnu.domain.M2OMember;
import edu.pnu.domain.O2OMember;
import edu.pnu.persistence.M2MMemberRepository;
import edu.pnu.persistence.M2MTable1Repository;
import edu.pnu.persistence.M2MTable2Repository;
import edu.pnu.persistence.M2OMemberRepository;
import edu.pnu.persistence.O2OMemberRepository;

@RestController
public class TestController {

	@Autowired private M2OMemberRepository m2oMRepo;
	@Autowired private O2OMemberRepository o2oMRepo;
	@Autowired private M2MMemberRepository m2mMRepo;
	@Autowired private M2MTable1Repository m2mT1Repo;
	@Autowired private M2MTable2Repository m2mT2Repo;
	
	@GetMapping
	public ResponseEntity<?> test01() {
		return ResponseEntity.ok("ok");
	}

	@GetMapping("/m2o")
	public ResponseEntity<?> m2o() {
		
		List<M2OMember> m = m2oMRepo.findAll();
		
		return ResponseEntity.ok(m);
	}
	
	@GetMapping("/o2o")
	public ResponseEntity<?> o2o() {
		
		List<O2OMember> m = o2oMRepo.findAll();
		
		return ResponseEntity.ok(m);
	}

	@GetMapping("/m2m")
	public ResponseEntity<?> m2m() {
		
		List<M2MMember> m = m2mMRepo.findAll();
		
		return ResponseEntity.ok(m);
	}
	
	@GetMapping("/m2mt1")
	public ResponseEntity<?> m2mt1() {
		
		List<M2MTable1> t = m2mT1Repo.findAll();
		
		return ResponseEntity.ok(t);
	}	

	@GetMapping("/m2mt2")
	public ResponseEntity<?> m2mt2() {
		
		List<M2MTable2> t = m2mT2Repo.findAll();
		
		// 결과를 JSON으로 변환할 때 table1set은 무시됨.(@JsonIgnore)
		// 포함하고 싶다면 DTO를 만들어서 전송하면 됨.
		
		return ResponseEntity.ok(t);
	}	
}
