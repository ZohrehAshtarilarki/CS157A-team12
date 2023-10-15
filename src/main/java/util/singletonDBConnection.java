package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Singleton Database Connection
public class singletonDBConnection implements DBConnectionInt {
    private static singletonDBConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456@Ebi";

    private singletonDBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        }
    }

    public static singletonDBConnection getInstance() {
        if (instance == null) {
            synchronized (singletonDBConnection.class) {
                if (instance == null) {
                    instance = new singletonDBConnection();
                }
            }
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
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
