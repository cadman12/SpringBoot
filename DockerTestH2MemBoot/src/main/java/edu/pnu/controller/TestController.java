package edu.pnu.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.entity.Member;
import edu.pnu.persistence.MemberRepository;

@RestController
public class TestController {

	@Autowired
	private MemberRepository memRepo;

	@GetMapping("/member")
	public ResponseEntity<?> getMembers() {
		return ResponseEntity.ok(memRepo.findAll());
	}

	@GetMapping("/member/{username}")
	public ResponseEntity<?> getMember(@PathVariable String username) {
		return memRepo.findById(username)
	            .map(findMember->ResponseEntity.ok(findMember))
	            .orElseGet(()->ResponseEntity.notFound().build());		
	}
	
	@PostMapping("/member")
	public ResponseEntity<?> postMember(@RequestBody Member member) {
		member.setJoinDate(new Date());
		return ResponseEntity.ok(memRepo.save(member));
	}

	@PutMapping("/member/{username}")
	public ResponseEntity<?> putMember(@PathVariable String username, @RequestBody Member member) {
	    return memRepo.findById(username)
	            .map(findMember->{
	                member.setUsername(username);
	                return ResponseEntity.ok(memRepo.save(member));
	            })
	            .orElseGet(()->ResponseEntity.notFound().build());
	}

	@PatchMapping("/member/{username}")
	public ResponseEntity<?> patchMember(@PathVariable String username, @RequestBody Member member) {
		return memRepo.findById(username)
	            .map(findMember->{
	                if (member.getPassword() != null)
	                	findMember.setPassword(member.getPassword());
	                if (member.getRole() != null)
	                	findMember.setRole(member.getRole());
	                return ResponseEntity.ok(memRepo.save(findMember));
	            })
	            .orElseGet(()->ResponseEntity.notFound().build());
	}

	@DeleteMapping("/member/{username}")
	public ResponseEntity<?> deleteMember(@PathVariable String username) {
		return memRepo.findById(username)
	            .map(findMember->{
	                memRepo.deleteById(username);
	                return ResponseEntity.ok("ok");
	            })
	            .orElseGet(()->ResponseEntity.notFound().build());
	}
}
