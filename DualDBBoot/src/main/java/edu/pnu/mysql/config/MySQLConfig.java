package edu.pnu.mysql.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
    basePackages = "edu.pnu.mysql.persistence", // MySQL용 리포지토리
    entityManagerFactoryRef = "mysqlEntityManagerFactory",
    transactionManagerRef = "mysqlTransactionManager"
)
public class MySQLConfig {

	@Autowired
	private Environment env;
	
    @Bean
    @Primary
    DataSource mysqlDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.mysql.driver-class-name"));
        dataSource.setJdbcUrl(env.getProperty("spring.datasource.mysql.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.mysql.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.mysql.password"));
        return dataSource;
    }

    @Primary
    @Bean(name = "mysqlEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(EntityManagerFactoryBuilder builder) {
    	Map<String, Object> properties = new HashMap<>();
    	properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
    	
        return builder
                .dataSource(mysqlDataSource())
                .packages("edu.pnu.mysql.entity") // MySQL용 엔티티
                .persistenceUnit("mysql")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean
    PlatformTransactionManager mysqlTransactionManager(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}