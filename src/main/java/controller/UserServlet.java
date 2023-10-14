package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import model.User;
import dal.UserDao;
/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Copying all the input parameters in to local variables
        int userID = Integer.parseInt(request.getParameter("userID"));
        String email = request.getParameter("email");
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        
        User user = new User();
        //Using Java Beans - An easiest way to play with group of related data
        user.setuserID(userID);
        user.setEmail(email);
        user.setUserName(userName);
        user.setPassword(password);
        
        UserDao userDao = new UserDao();
        
        //Insert user data in to the database
        String userRegistered = userDao.registerUser(user);
        
      //On success, display a message to user on Home page
        if(userRegistered.equals("SUCCESS")) {
        	request.getRequestDispatcher("/Home.jsp").forward(request, response);
        }
        //On Failure, display a meaningful message to the user
        else {
        	request.setAttribute("errMessage", userRegistered);
        	request.getRequestDispatcher("/Register.jsp").forward(request, response);
        }
	}

}
