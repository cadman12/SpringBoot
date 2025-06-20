package edu.pnu.h2.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "edu.pnu.h2.persistence", // H2용 리포지토리
    entityManagerFactoryRef = "h2EntityManagerFactory",
    transactionManagerRef = "h2TransactionManager"
)
public class H2Config {

	@Autowired
	private Environment env;

    @Bean
    DataSource h2DataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.h2.driver-class-name"));
        dataSource.setJdbcUrl(env.getProperty("spring.datasource.h2.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.h2.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.h2.password"));
        return dataSource;    	
    }

    @Bean(name = "h2EntityManagerFactory")
    LocalContainerEntityManagerFactoryBean h2EntityManagerFactory(EntityManagerFactoryBuilder builder) {
    	Map<String, Object> properties = new HashMap<>();
    	properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

    	return builder
                .dataSource(h2DataSource())
                .packages("edu.pnu.h2.entity") // H2용 엔티티
                .persistenceUnit("h2")
                .properties(properties)
                .build();
    }

    @Bean
    PlatformTransactionManager h2TransactionManager(@Qualifier("h2EntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}