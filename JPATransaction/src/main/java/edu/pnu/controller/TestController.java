package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.service.TestService;

@Transactional
@RestController
public class TestController {

	@Autowired
	private TestService testService;
	
	@PostMapping("/join")
	public boolean join() {
		return testService.join();
	}
}
