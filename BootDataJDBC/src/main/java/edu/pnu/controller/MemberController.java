package edu.pnu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	
	@GetMapping("/member1")
	public ResponseEntity<?> getMembersType1() {
		return ResponseEntity.ok(memberService.getMembersType1());
	}

	@GetMapping("/member2")
	public ResponseEntity<?> getMembersType2() {
		return ResponseEntity.ok(memberService.getMembersType21());
	}

	@GetMapping("/member3")
	public ResponseEntity<?> getMembersType3() {
		return ResponseEntity.ok(memberService.getMembersType22());
	}
}
