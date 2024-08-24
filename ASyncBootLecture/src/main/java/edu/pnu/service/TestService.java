package edu.pnu.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TestService {

	@Async
	public void print(String message) {
		System.out.println(message);
	}
}
