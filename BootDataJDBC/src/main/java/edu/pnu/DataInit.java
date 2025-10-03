package edu.pnu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements ApplicationRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		jdbcTemplate.execute("create table member("
				+ "username varchar(16) primary key not null,"
				+ "password varchar(16) not null,"
				+ "role varchar(16) not null default 'ROLE_USER',"
				+ "enabled boolean not null default true)");
		jdbcTemplate.execute("insert into member(username,password,role,enabled) values ('scott', 'tiger', 'ROLE_USER', true)");
		jdbcTemplate.execute("insert into member(username,password,role,enabled) values ('tom', 'tiger', 'ROLE_ADMIN', true)");
	}
}
