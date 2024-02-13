package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Singleton Database Connection
public class singletonDbConnection implements DbConnectionInt {
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/event_management";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Samm9009";

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

    //Instance holder pattern helps manage resources efficiently, ensuring that the overhead of connecting to
    // the database is incurred just once.
    // This inner class holds the instance of the database connection
    private static final class InstanceHolder {
        private static final singletonDbConnection instance = new singletonDbConnection();
    }

    public static singletonDbConnection getInstance() {
        return InstanceHolder.instance;
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

