package jm.task.core.jdbc.util;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Util {
	private static final String URL = "jdbc:mysql://localhost:3306/mydb?useSSL=false";
	private static final String USERNAME = "Root12";
	private static final String PASSWORD = "root";

	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			connection.setAutoCommit(false);
		}catch (SQLException | ClassNotFoundException e){
				e.printStackTrace();
		}
		return connection;
	}
}
