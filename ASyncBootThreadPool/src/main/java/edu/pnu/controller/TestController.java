package edu.pnu.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.service.TestService;
import edu.pnu.service.TestService1;

@RestController
public class TestController {

	@Autowired
	private TestService testService;

	@Autowired
	private TestService1 testService1;
	
	// 리턴이 필요한 경우
	@GetMapping("/test")
	@ResponseStatus(HttpStatus.OK)
	public void printMessage() {
		try {
			for (int i = 1 ; i <= 10 ; i++) {
				CompletableFuture<String> completableFuture = testService.print(i + "");
				completableFuture.thenAccept((m)->System.out.println(m))
								.exceptionally(e->{
									System.out.println(e.getMessage());
									return null;
								});
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// 리턴이 필요없는 경우
	@GetMapping("/test1")
	@ResponseStatus(HttpStatus.OK)
	public void printMessage1() {
		for (int i = 1 ; i <= 10 ; i++) {
			testService1.print(i + "");
		}
	}	
}