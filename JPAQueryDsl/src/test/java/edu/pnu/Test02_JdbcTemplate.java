package edu.pnu;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import edu.pnu.domain.Customer;
import edu.pnu.util.TestUtil;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
class Test02_JdbcTemplate {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@DisplayName("JdbcTemplate - dataInsert")
	@Test
	@Order(1)
	void dataInsert() throws SQLException {
		
		TestUtil.drawTitle("JdbcTemplate - dataInsert");

		// 1개 입력
		int result = jdbcTemplate.update("insert into customer(first_name, last_name) values (?,?)", "Bob", "Smith");
		
		List<Customer> list = new ArrayList<>();
		list.add(Customer.builder().firstName("Bob").lastName("Wilson").build());
		list.add(Customer.builder().firstName("길동").lastName("홍").build());
		
		// batch 입력
		// int[] batchUpdate(String sql, BatchPreparedStatementSetter pss)
		int[] res = jdbcTemplate.batchUpdate("insert into customer(first_name, last_name) values (?,?)",
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Customer cust = list.get(i);
						ps.setString(1, cust.getFirstName());
						ps.setString(2, cust.getLastName());
					}
					@Override
					public int getBatchSize() {
						return list.size();
					}
				}
		);
		for(int r : res) result+=r;
		System.out.println(String.format("%d개의 행이 입력되었습니다.", result));
	}

	@DisplayName("JdbcTemplate - dataSelect1")
	@Test
	@Order(2)
	void dataSelect1() throws SQLException {
		
		TestUtil.drawTitle("JdbcTemplate - dataSelect1");

		jdbcTemplate.query("select * from customer", (rs->{
			System.out.println(rs.getLong("id")+","+rs.getString("first_name")+","+rs.getString("last_name"));
		}));
	}
	
	@DisplayName("JdbcTemplate - dataUpdate")
	@Test
	@Order(3)
	void dataUpdate() throws SQLException {
		
		TestUtil.drawTitle("JdbcTemplate - dataUpdate");

		int result = jdbcTemplate.update("update customer set first_name=?, last_name=? where id=?", "순신", "이", 2);
		System.out.println(String.format("%d개의 행이 수정되었습니다.", result));
	}	

	@DisplayName("JdbcTemplate - dataSelect2")
	@Test
	@Order(4)
	void dataSelect2() throws SQLException {
		
		TestUtil.drawTitle("JdbcTemplate - dataSelect2");

		List<Customer> list = new ArrayList<>();
		jdbcTemplate.query("select * from customer where id=?", (rs->{
			list.add(Customer.builder()
							.id(rs.getLong("id"))
							.firstName(rs.getString("first_name"))
							.lastName(rs.getString("last_name"))
							.build());
		}), 2);

		for(Customer c : list) {
			System.out.println(c);
		}
	}

	@DisplayName("JdbcTemplate - dataDelete")
	@Test
	@Order(5)
	void dataDelete() throws SQLException {
		
		TestUtil.drawTitle("JdbcTemplate - dataDelete");

		int result = jdbcTemplate.update("delete from customer where first_name like ?", "Bob");
		System.out.println(String.format("%d개의 행이 삭제되었습니다.", result));
	}
	
	@DisplayName("JdbcTemplate - dataSelect3")
	@Test
	@Order(6)
	void dataSelect3() throws SQLException {
		
		TestUtil.drawTitle("JdbcTemplate - dataSelect3");

		jdbcTemplate.query("select * from customer", (rs->{
			System.out.println(rs.getLong("id")+","+rs.getString("first_name")+","+rs.getString("last_name"));
		}));
	}
	
	@DisplayName("jdbcTemplate - batchUpdate")
	@Order(7)
	@Test
	void batchUpdate() throws SQLException {
		
		TestUtil.drawTitle("JdbcTemplate - batchUpdate");
		
		//List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
		//        .map(name -> name.split(" "))
		//        .collect(Collectors.toList());

		List<Object[]> splitUpNames = new ArrayList<>();
		List<String> nameList = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long");
		nameList.forEach(name->{
			splitUpNames.add(name.split(" "));
		});
		
		// int[] batchUpdate(String sql, List<Object[]> batchArgs)
		int[] res = jdbcTemplate.batchUpdate("insert into customer(first_name, last_name) values (?,?)", splitUpNames);
		int result = 0;
		for(int r : res) result+=r;
		System.out.println(String.format("%d개의 행이 입력되었습니다.", result));
	}
	
	@DisplayName("JdbcTemplate - dataSelect4")
	@Test
	@Order(8)
	void dataSelect4() throws SQLException {
		
		TestUtil.drawTitle("JdbcTemplate - dataSelect4");

		jdbcTemplate.query("select * from customer", (rs->{
			System.out.println(rs.getLong("id")+","+rs.getString("first_name")+","+rs.getString("last_name"));
		}));
	}		
}