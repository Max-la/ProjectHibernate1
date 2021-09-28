package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl extends Util implements UserDao {
    Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }


    @Override
    public void createUsersTable() throws SQLSyntaxErrorException {
        PreparedStatement preparableStatement = null;
        String sql = "CREATE TABLE `mydb`.`Users` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastName` VARCHAR(45) NOT NULL,\n" +
                "age INT NOT NULL,\n" +
                "  PRIMARY KEY (`id`, `name`, `lastName`,age));";

        try {
            preparableStatement = connection.prepareStatement(sql);
            preparableStatement.execute();
        } catch (SQLException ignored) {

        } finally {
            if (preparableStatement != null) {
                try {
                    preparableStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void dropUsersTable() throws SQLException {

        String sql = "DROP TABLE USERS";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ignore) {

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws SQLSyntaxErrorException {
        PreparedStatement preparedStatement = null;
        String sql1 = "insert into USERS(name,lastName,age) values (?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setLong(3, age);
            preparedStatement.executeUpdate();

            System.out.println("User с именем – " + name + " добавлен в базу");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void removeUserById(long id) throws SQLException {

        String sql = "delete from USERS where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();

        String sql = "SELECT id,name,lastName,age FROM USERS";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge((byte) resultSet.getLong("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(userList.toString());
        return userList;
    }

    @Override
    public void cleanUsersTable() throws SQLException {

        String sql = "delete from USERS";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
