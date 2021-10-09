package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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

	public SessionFactory getSessionFactory() {
		SessionFactory sessionFactory = null;
		try {
			Configuration configuration = new Configuration();
			Properties setting = new Properties();

			setting.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
			setting.put(Environment.URL, URL);
			setting.put(Environment.USER, USERNAME);
			setting.put(Environment.PASS, PASSWORD);
			setting.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
			setting.put(Environment.SHOW_SQL, "true");
			setting.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

			configuration.setProperties(setting);
			configuration.addAnnotatedClass(User.class);
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();

			sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sessionFactory;
	}
}