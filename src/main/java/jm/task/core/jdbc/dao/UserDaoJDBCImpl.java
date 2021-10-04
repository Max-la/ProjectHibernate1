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
    public void createUsersTable() {
        PreparedStatement preparableStatement = null;
        try {
            preparableStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `mydb`.`Users` (\n" +
                    "`id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                    "`name` VARCHAR(45) NOT NULL,\n" +
                    "`lastName` VARCHAR(45) NOT NULL,\n" +
                    "`age` TINYINT NOT NULL,\n" +
                    "PRIMARY KEY (`id`, `name`, `lastName`,age));");

            preparableStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            try {
                if (preparableStatement != null)
                    preparableStatement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS USERS");

            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age){
        PreparedStatement preparedStatement = null;
        try{
            try{
                preparedStatement = connection.prepareStatement("insert into USERS(name,lastName,age) values (?,?,?)");
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                preparedStatement.executeUpdate();
                connection.commit();
                System.out.println("User с именем – " + name + " добавлен в базу");
            } catch (SQLException e) {
                connection.rollback();
                if(preparedStatement != null){
                    preparedStatement.close();
                }
                connection.close();
                e.printStackTrace();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("delete from USERS where id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers(){
        List<User> userList = new ArrayList<>();
        try{
            try{
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setAge(resultSet.getByte("age"));
                    userList.add(user);
                }
                connection.commit();
                statement.close();
            } catch (SQLException e) {
                connection.rollback();
                connection.close();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(userList.toString());
        return userList;
    }

    @Override
    public void cleanUsersTable(){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE TABLE USERS");
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

    }
}
