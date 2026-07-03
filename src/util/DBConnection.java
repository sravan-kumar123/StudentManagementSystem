package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Central place to grab a JDBC connection to the Oracle DB.
 * Update the constants below to match your local Oracle setup
 * (XE / OracleDB free tier / whatever you're running).
 */
public class DBConnection {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "system";
    private static final String PASSWORD = "your_password";

    private static Connection connection;

    private DBConnection() {
        // utility class, no instances
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC driver not found. Make sure ojdbc jar is on the classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Could not connect to the database: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
