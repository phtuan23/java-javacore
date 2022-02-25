package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
	//btl_javacore
	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=btl_javacore";
	private static final String USER = "sa";
	private static final String PASSWORD = "123456";
	private static Connection connection;
	
	private ConnectionDB() {
	}
	
	public static Connection getConnection() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(URL,USER,PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
}
