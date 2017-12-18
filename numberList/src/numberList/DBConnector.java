package numberList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	
	private static Connection conn = null;
	
	public DBConnector(){
		
	}
	
	public static Connection DBConnector1() {
		try {
			if (conn == null) {

				Class.forName("oracle.jdbc.driver.OracleDriver");

				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "tanoshi", "tanoshi");
				conn.setAutoCommit(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static void close() {
		try {
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
