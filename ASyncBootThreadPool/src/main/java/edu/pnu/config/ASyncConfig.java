package edu.pnu.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ASyncConfig {

	@Bean("threadPoolTaskExecutor")
	Executor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(30);
		taskExecutor.setQueueCapacity(100);
		taskExecutor.setThreadNamePrefix("Executor-");		
		
		return taskExecutor;
	}
}
