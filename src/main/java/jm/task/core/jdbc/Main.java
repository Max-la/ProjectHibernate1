package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.SQLException;

public class Main {
	public static void main(String[] args) throws SQLException {

		new UserServiceImpl().createUsersTable();
		new UserServiceImpl().saveUser("Ivan", "WWW", (byte) 56);
		new UserServiceImpl().saveUser("Stepan", "Petrov", (byte) 34);
		new UserServiceImpl().saveUser("Igor", "Petrov", (byte) 44);
                new UserServiceImpl().saveUser("Evgenyi", "Ivanov", (byte) 22);
		new UserServiceImpl().getAllUsers();
		new UserServiceImpl().cleanUsersTable();
		new UserServiceImpl().dropUsersTable();
	}
}
