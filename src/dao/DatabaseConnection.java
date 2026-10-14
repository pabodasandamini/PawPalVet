package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/pawpal_vet";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root123";   

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("✅ Database connected!");
            } catch (ClassNotFoundException e) {
                System.out.println("❌ MySQL JDBC Driver not found!");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("❌ Connection failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try { 
                connection.close(); 
            } catch (SQLException e) { 
                e.printStackTrace(); 
            }
        }
    }
}