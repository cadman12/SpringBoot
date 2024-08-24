package edu.pnu.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@EnableAsync
@Service
public class TestService {

	@Async
	public CompletableFuture<String> print(String message) {
		System.out.println("Task Start = " + message);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Task End = " + message);
		return CompletableFuture.completedFuture("Hello");
	}
}
