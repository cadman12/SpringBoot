package edu.pnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ASyncBootThreadApplication {

	public static void main(String[] args) {
		SpringApplication.run(ASyncBootThreadApplication.class, args);
	}

}
