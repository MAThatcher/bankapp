package dev.thatcher.app;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JDBCconnection {
	private static Connection conn = null;

	public static void main(String[] args) {
		Connection con1 = getConnection();
		System.out.println(con1);
	}

	public static Connection getConnection() {
		try {
			if (conn == null) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Properties props = new Properties();
				FileInputStream input = new FileInputStream("src/main/resources/connection.properties");
				props.load(input);
				conn = DriverManager.getConnection(props.getProperty("url"), props.getProperty("username"), props.getProperty("password"));
				return conn;
			} else {
				return conn;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
