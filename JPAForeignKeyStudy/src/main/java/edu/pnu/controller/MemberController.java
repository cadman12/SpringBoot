package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.persistence.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class MemberController {

	@Autowired
	MemberRepository memRepo;

	@Autowired
	HttpSession httpSession;

	@GetMapping("/")
	public ResponseEntity<?> test() {
		return ResponseEntity.ok("OK");
	}

	@GetMapping("/s")
	public ResponseEntity<?> tests(HttpServletRequest req) {
		req.getCookies();
		return ResponseEntity.ok("OK1");
	}
	
	@GetMapping("/ss")
	public ResponseEntity<?> testss() {
		return ResponseEntity.ok(httpSession.getId());
	}
	
	@GetMapping("/member/{username}")
	public ResponseEntity<?> deleteMember(@PathVariable String username) {
		memRepo.deleteById(username);
		return ResponseEntity.ok("Deleted!");
	}
}
