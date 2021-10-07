package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.SQLException;

public class Main {
	public static void main(String[] args) throws SQLException {

		UserServiceImpl userService = new UserServiceImpl();

		userService.createUsersTable();
		userService.saveUser("Ivan", "WWW", (byte) 56);
		userService.saveUser("Stepan", "Petrov", (byte) 34);
		userService.saveUser("Igor", "Petrov", (byte) 44);
                userService.saveUser("Evgenyi", "Ivanov", (byte) 22);
                userService.removeUserById(2);
		userService.getAllUsers();
		userService.cleanUsersTable();
		userService.dropUsersTable();
	}

}

