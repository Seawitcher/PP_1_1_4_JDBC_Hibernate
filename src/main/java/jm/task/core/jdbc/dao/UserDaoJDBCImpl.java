package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        final String sql = "CREATE TABLE if not exists `users` (\n" +
                "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(45) NOT NULL,\n" +
                "  `lastName` varchar(45) NOT NULL,\n" +
                "  `age` int NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ";
        try(Statement statement =connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute(sql);
            connection.commit();
        } catch (SQLException e) {
           e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void dropUsersTable() {
        final String sql = "DROP TABLE IF EXISTS users;";
        try(Statement statement =connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute(sql);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        final String sql = "INSERT INTO users(name,lastName,age) values (?, ?, ?);";
        try( PreparedStatement preparedStatement =connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setLong(3,age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void removeUserById(long id) {
        final String sql = "DELETE FROM users WHERE id=?;";
        try(PreparedStatement preparedStatement =connection.prepareStatement(sql)){
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
        e.printStackTrace();
            try {
                connection.rollback();


            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList <> ();
        String sql = "SELECT * FROM users;";
        try(Statement statement =connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
            connection.setAutoCommit(false);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUsers.add(user);
            }
            connection.commit();
    } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        final String sql = "TRUNCATE TABLE users;";
        try(Statement statement =connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute(sql);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }
}
