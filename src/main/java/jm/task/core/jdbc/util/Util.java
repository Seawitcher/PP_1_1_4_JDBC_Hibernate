package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql:// localhost:3306/mybd";
    private static final String USER = "root";
    private static final String PASS = "root";
    public static Connection getConnection() {
    Connection connection = null;

        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connection - Ok!");
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Connection - Error!");
        }
        return connection;
    }
}


