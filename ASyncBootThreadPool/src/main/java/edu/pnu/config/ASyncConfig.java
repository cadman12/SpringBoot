package edu.pnu.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ASyncConfig {

	@Bean(name = "threadPoolTaskExecutor")
	Executor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

		taskExecutor.setCorePoolSize(5);	// 동시에 실행할 기본 스레드 수 설정, Default = 1
		taskExecutor.setMaxPoolSize(30);	// 스레드 풀에서 사용할 최대 스레드 수 설정, Default = Integer.MAX_VALUE
		taskExecutor.setQueueCapacity(100);	// 스레드 풀 executor의 작업 큐 크기 설정, Default = Integer.MAX_VALUE
		taskExecutor.initialize();			// 초기화 (생략 가능 : Bean으로 등록될 때 자동으로 초기화 됨.)

		// ==>	최초 5개의 스레드에서 처리하다가 요청이 많아져서 처리 속도가 밀릴 경우 100개 사이즈의 큐에서 입력해서 대기하고
		//		큐 사이즈를 넘어선 요청이 발생하면 최대 30개의 스레드를 생성해서 처리하도록 설정

		return taskExecutor;
	}
}
