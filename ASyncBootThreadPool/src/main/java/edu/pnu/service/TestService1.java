package edu.pnu.service;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@EnableAsync
@Service
public class TestService1 {

	private final Executor executor;
	
	public TestService1(@Qualifier("threadPoolTaskExecutor") Executor executor) {
		this.executor = executor;
	}
	
	@Async
	public void print(String message) {
		
		Runnable runnable = ()->{
			System.out.println("Task Start = " + message);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("Task End = " + message);
		};
		executor.execute(runnable);
	}
}
