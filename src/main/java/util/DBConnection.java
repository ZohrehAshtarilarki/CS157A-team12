package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection createConnection() {
		Connection con = null;
		//MySQL URL followed by the database name
		String url = "jdbc:mysql://localhost:3306/EventManagement"; 
		String username = "root"; //MySQL username
		String password = "123456@Ebi"; //MySQL password
		System.out.println("In DBConnection.java class ");

		try {
			try {
				//loading MySQL drivers
				Class.forName("com.mysql.cj.jdbc.Driver");
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			//attempting to connect to MySQL database
			con = DriverManager.getConnection(url, username, password);
			System.out.println("Printing connection object " + con);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
}
