package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.service.TestService;

@RestController
public class TestController {

	@Autowired
	private TestService testService;
	
	@GetMapping("/test")
	@ResponseStatus(HttpStatus.OK)
	public void printMessage() {
		for (int i = 1 ; i <= 100 ; i++) {
			testService.print(i + "");
		}
	}
}