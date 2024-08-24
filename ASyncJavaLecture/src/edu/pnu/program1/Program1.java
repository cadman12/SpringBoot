package edu.pnu.program1;

class MessageService {
	public void print(String message) {
		System.out.println(message);
	}
}

public class Program1 {
	
	public static void main(String[] args) {
		MessageService messageService = new MessageService();
		
		for (int i = 1 ; i <= 100 ; i++) {
			messageService.print(i + "");
		}
	}
}
