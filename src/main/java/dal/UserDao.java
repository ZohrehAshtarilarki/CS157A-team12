package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.User;
import util.DBConnection;


public class UserDao {
	public String registerUser(User user) {
		int userID = user.getuserID();
		String email = user.getEmail();
		String userName = user.getUserName();
		String password = user.getPassword();
		
		Connection con = null; 
		PreparedStatement preparedStatement = null;
		try {
			con = DBConnection.createConnection();
			//Insert user details into the table 'User'
			String query = "Insert Into User(SJSUID, SJSUEmail, UserName,"
					+ "Password) values (?,?,?,?,?)";
			//Making use of prepared statements here to insert bunch of data
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, userName);
			preparedStatement.setString(4, password);

			int i= preparedStatement.executeUpdate();
			
			//Just to ensure data has been inserted into the database
			if (i != 0) return "SUCCESS";
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		// On failure, send a message from here
		return "Oops.. Something went wrong there..!";
	}
}
