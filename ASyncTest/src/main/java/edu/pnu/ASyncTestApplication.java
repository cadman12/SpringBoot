package edu.pnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ASyncTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ASyncTestApplication.class, args);
	}

}
