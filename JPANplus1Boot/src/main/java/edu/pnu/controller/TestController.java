package edu.pnu.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.MemberDTO;
import edu.pnu.service.TestService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TestController {

	private final TestService testService;

	// 1. fetchType을 LAZY로 두고 실행하면 질의가 총 (1+5)번 발생함
	// 테스트를 하려면 Member.java에서 @BatchSize를 주석처리하고 테스트
	/*
		select m.username,m.enabled,m.password,m.role from member m;
		select b.username,b.id,b.content,b.regi_date,b.title from board b where b.username=?;
		select b.username,b.id,b.content,b.regi_date,b.title from board b where b.username=?;
		select b.username,b.id,b.content,b.regi_date,b.title from board b where b.username=?;
		select b.username,b.id,b.content,b.regi_date,b.title from board b where b.username=?;
		select b.username,b.id,b.content,b.regi_date,b.title from board b where b.username=?;
	 */
	@GetMapping("/member")
	public ResponseEntity<?> member() {
		List<MemberDTO> list = testService.member();
		return ResponseEntity.ok(list);
	}
	
	// 2. 패치 조인 ==> 조인 질의 1번만 발생함.
	/*
		select m.username,b.username,b.id,b.content,b.regi_date,b.title,m.enabled,m.password,m.role
		from member m
		join board b on m.username=b.username;
	 */
	@GetMapping("/member1")
	public ResponseEntity<?> member1() {
		List<MemberDTO> list = testService.member1();
		return ResponseEntity.ok(list);
	}
	
	// 3. 엔티티 그래프 ==> 조인 질의 1번만 발생함.
	/*
	 	select m.username,b.username,b.id,b.content,b.regi_date,b.title,m.enabled,m.password,m.role
		from member m
		left join board b on m.username=b.username;
	 */
	@GetMapping("/member2")
	public ResponseEntity<?> member2() {
		List<MemberDTO> list = testService.member2();
		return ResponseEntity.ok(list);
	}
	
	// 4. Batch Size ==> 그냥 실행했을 때 발생하는 추가 질의 개수를 배치 사이즈로 나눈 만큼 추가 질의가 발생함.
	// 이 경우에는 추가 질의 횟수가 5회이고 BatchSize를 5로 주었기 때문에 1번만 추가 질의가 발생함.
	// 테스트를 하려면 Member.java에서 @BatchSize를 주석해제하고 테스트
	/*
		select m.username,m.enabled,m.password,m.role from member m;
		select b.username,b.id,b.content,b.regi_date,b.title from board b where b.username in (?,?,?,?,?);
	 */
	@GetMapping("/member3")
	public ResponseEntity<?> member3() {
		List<MemberDTO> list = testService.member3();
		return ResponseEntity.ok(list);
	}		
}
