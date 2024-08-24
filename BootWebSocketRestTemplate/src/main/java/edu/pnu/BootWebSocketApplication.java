package edu.pnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling		// Boot Scheduling 활성화 (@Scheduled) ==> Emulator
@SpringBootApplication
public class BootWebSocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootWebSocketApplication.class, args);
	}

}
