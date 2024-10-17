package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;

@RestController
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping("/member")
	public ResponseEntity<?> memberQuery(Member member) {
		//return ResponseEntity.ok(memberService.memberQuery(member));
		return null;
	}
}
