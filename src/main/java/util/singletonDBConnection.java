package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Singleton Database Connection
public class singletonDbConnection implements DbConnectionInt {
    private static singletonDbConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/EventManagement";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456@Ebi";

    private singletonDbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.print("connecting successfully");
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        }
    }

    public static singletonDbConnection getInstance() {
        if (instance == null) {
            synchronized (singletonDbConnection.class) {
                if (instance == null) {
                    instance = new singletonDbConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed() || !connection.isValid(2)) {
                System.out.println("Connection either closed or not valid. connecting again...");
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.print("connecting successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        }
        return connection;
    }

    @Override
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle exceptions appropriately later
            }
        }
    }
}

