package edu.pnu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Configuration
public class CustomConfig {

	// shared object이기 때문에 Transaction을 설정할 수 없음.
	// insert, update, delete를 실행하기 위한 목적으로 사용할 수 없음.
	@PersistenceContext
	private EntityManager entityManager;	

	@Bean
	JPAQueryFactory queryFactory() {
		return new JPAQueryFactory(entityManager);
	}	
}
