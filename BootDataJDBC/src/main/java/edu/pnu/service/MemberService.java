package edu.pnu.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final DataSource dataSource;
	private final JdbcTemplate jdbcTemplate;

	// DataSource를 이용한 방법
	public List<Member> getMembersType1() {
		
		List<Member> list = new ArrayList<>();
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from member order by username");
			while(rs.next()) {
				list.add(Member.builder()
								.username(rs.getString("username"))
								.password(rs.getString("password"))
								.role(rs.getString("role"))
								.enabled(rs.getBoolean("enabled"))
								.build());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null)		rs.close();
				if (stmt != null)	stmt.close();
				if (con != null)	con.close();
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
		}
		return list;
	}
	
	// JdbcTemplate + ResultSetExtractor를 이용한 방법
	public List<Member> getMembersType21() {
		return jdbcTemplate.query("select * from member order by username", rs -> {
			List<Member> list = new ArrayList<>();	
			while(rs.next()) {
				list.add(Member.builder()
								.username(rs.getString("username"))
								.password(rs.getString("password"))
								.role(rs.getString("role"))
								.enabled(rs.getBoolean("enabled"))
								.build());
			}
			return list;
		});
	}
	
	// JdbcTemplate + RowMapper를 이용한 방법
	public List<Member> getMembersType22() {
		return jdbcTemplate.query("select * from member order by username", (rs, rowNum) -> {
			return Member.builder()
					.username(rs.getString("username"))
					.password(rs.getString("password"))
					.role(rs.getString("role"))
					.enabled(rs.getBoolean("enabled"))
					.build();
		});
	}
}
