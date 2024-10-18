package edu.pnu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Customer;
import edu.pnu.util.TestUtil;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
class Test01_JDBC {

	// javax.sql에 정의된 Interface, 구현체:com.zaxxer.hikari.HikariDataSource
	@Autowired
	private DataSource dataSource;

	@DisplayName("DataSource - dataInsert")
	@Test
	@Order(1)
	void dataInsert() throws SQLException {
		
		TestUtil.drawTitle("DataSource - dataInsert");
		
		Connection con = dataSource.getConnection();
		PreparedStatement psmt = con.prepareStatement("insert into customer(first_name, last_name) values (?,?)");
		psmt.setString(1, "Bob");
		psmt.setString(2, "Smith");
		int result = psmt.executeUpdate();
		psmt.setString(1, "Bob");
		psmt.setString(2, "Wilson");
		result += psmt.executeUpdate();
		psmt.setString(1, "길동");
		psmt.setString(2, "홍");
		result += psmt.executeUpdate();
		psmt.close();
		con.close();
		
		System.out.println(String.format("%d개의 행이 입력되었습니다.", result));
	}

	@DisplayName("DataSource - dataSelect1")
	@Test
	@Order(2)
	void dataSelect1() throws SQLException {
		
		TestUtil.drawTitle("DataSource - dataSelect1");
		
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from customer");

		while(rs.next()) {
			System.out.println(rs.getLong("id") + "," + rs.getString("first_name") + "," + rs.getString("last_name"));
		}
		
		rs.close();
		stmt.close();
		con.close();
	}
	
	@DisplayName("DataSource - dataUpdate")
	@Test
	@Order(3)
	void dataUpdate() throws SQLException {
		
		TestUtil.drawTitle("DataSource - dataUpdate");
		
		String sql = "update customer set first_name=?,last_name=? where id=?";
		
		Connection con = dataSource.getConnection();
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1, "순신");
		psmt.setString(2, "이");
		psmt.setInt(3, 2);
		int result = psmt.executeUpdate();
		System.out.println(String.format("%d개의 행이 수정되었습니다.", result));
		
		psmt.close();
		con.close();
	}

	@DisplayName("DataSource - dataSelect2")
	@Test
	@Order(4)
	void dataSelect2() throws SQLException {
		
		TestUtil.drawTitle("DataSource - dataSelect2");
		
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from customer");

		while(rs.next()) {
			Customer cust = Customer.builder()
					.id(rs.getLong("id"))
					.firstName(rs.getString("first_name"))
					.lastName(rs.getString("last_name")).build();
			System.out.println(cust);
		}
		
		rs.close();
		stmt.close();
		con.close();
	}
	@DisplayName("DataSource - dataDelete")
	@Test
	@Order(5)
	void dataDelete() throws SQLException {
		
		TestUtil.drawTitle("DataSource - dataDelete");
		
		String sql = "delete from customer where id=?";
		
		Connection con = dataSource.getConnection();
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setInt(1, 2);
		int result = psmt.executeUpdate();
		System.out.println(String.format("%d개의 행이 삭제되었습니다.", result));
		
		psmt.close();
		con.close();
	}

	@DisplayName("DataSource - dataSelect3")
	@Test
	@Order(6)
	void dataSelect3() throws SQLException {
		
		TestUtil.drawTitle("DataSource - dataSelect3");
		
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from customer");

		while(rs.next()) {
			Customer cust = Customer.builder()
					.id(rs.getLong("id"))
					.firstName(rs.getString("first_name"))
					.lastName(rs.getString("last_name")).build();
			System.out.println(cust);
		}
		
		rs.close();
		stmt.close();
		con.close();
	}
}





