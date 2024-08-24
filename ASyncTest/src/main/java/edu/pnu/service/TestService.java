package edu.pnu.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TestService {
	
	@Async
	public void test() {
		System.out.println("TestService : test() in");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("TestService : test() out");
	}

	@Async
	public CompletableFuture<String> test1() {
		System.out.println("TestService : test1() in");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("TestService : test1() out");
		return CompletableFuture.completedFuture("CompletedFuture");
	}
	
}
