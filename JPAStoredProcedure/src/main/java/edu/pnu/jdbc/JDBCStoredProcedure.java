package edu.pnu.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;

// Java Application으로 실행
public class JDBCStoredProcedure {

	public static void main(String[] args) throws Exception {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "scott", "tiger");
		
		// 저장 프로시저 호출 객체 생성
		CallableStatement cs = con.prepareCall("call film_in_stock(?,?,?)");

		// IN 파라미터 변수 설정
		cs.setInt(1, 1);
		cs.setInt(2, 1);

		// OUT 파라미터 변수 설정
		cs.registerOutParameter(3, Types.INTEGER);

		// 저장 프로시저 실행 (true:ResultSet, false:UpdateCount)
        boolean isResultSet = cs.execute();
        do {
        	if (isResultSet) {
        		ResultSet rs = cs.getResultSet();
        		ResultSetMetaData rm = rs.getMetaData();
        		while(rs.next()) {
	        		for(int i = 1 ; i <= rm.getColumnCount() ; i++) {
	        			if (i != 1)	System.out.print(",");
	        			System.out.print(rs.getString(i));
	        		}
	        		System.out.println();
        		}        		
        	}
        	else {
	        	System.out.println("updateCount:" + cs.getUpdateCount());
        	}
	        // 저장 프로시저 다음 질의 결과
        	isResultSet = cs.getMoreResults();
        } while(isResultSet || cs.getUpdateCount() != -1);

        System.out.println("p_film_count:" + cs.getInt(3));
	}
}
