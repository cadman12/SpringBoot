package edu.pnu.program3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MessageService {

	private final ExecutorService executorService = Executors.newFixedThreadPool(10);

	public void print(String message) {
		executorService.submit(()-> System.out.println(message));
	}
}

public class Program3 {
	
	public static void main(String[] args) {
		MessageService messageService = new MessageService();
		
		for (int i = 1 ; i <= 100 ; i++) {
			messageService.print(i + "");
		}
	}
}
