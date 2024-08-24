package edu.pnu.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.service.TestService;

@RestController
public class TestController {

	@Autowired
	private TestService testService;
	
	@GetMapping("/test")
	public String test() {
		
		System.out.println("TestController : test() in");

		testService.test();

		System.out.println("TestController : test() out");
		
		return "Test";
	}
	
	@GetMapping("/test1")
	public String test1() {
		
		System.out.println("TestController : test1() in");

		CompletableFuture<String> future = testService.test1();
		
		future.thenAccept(result-> {
			System.out.println("CallBack: " + result);
		});

		System.out.println("TestController : test1() out");
		
		return "Test";
	}	
}
