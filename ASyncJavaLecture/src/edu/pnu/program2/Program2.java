package edu.pnu.program2;

class MessageService {
	public void print(String message) {
		new Thread(()->{
			System.out.println(message);
		}).start();
	}
}

public class Program2 {
	
	public static void main(String[] args) {
		MessageService messageService = new MessageService();
		
		for (int i = 1 ; i <= 100 ; i++) {
			messageService.print(i + "");
		}
	}
}
