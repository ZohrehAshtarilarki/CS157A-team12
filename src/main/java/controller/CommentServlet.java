package controller;

import java.io.IOException;
import java.util.List;

import dal.CommentDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Comment;

@WebServlet(name = "CommentServlet", urlPatterns = { "/CommentServlet" })
public class CommentServlet extends HttpServlet {
	private CommentDAO commentDAO;

	@Override
	public void init() throws ServletException {
		super.init();
		commentDAO = new CommentDAO();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action != null) {
			switch (action) {
				case "addComment":
					addComment(request, response);
					break;
				default:
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action != null) {
			switch (action) {
				case "getAllCommentByEvent":
					getAllCommentByEvent(request, response);
					break;
				default:
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
			}
		}
	}

	private void addComment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int eventID = Integer.parseInt(request.getParameter("eventID"));
			int sjsuID = Integer.parseInt(request.getParameter("sjsuID"));
			String text = request.getParameter("commentText");

			commentDAO.addComment(eventID, sjsuID, text);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/eventInfo.jsp");
			dispatcher.forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}

	public void getAllCommentByEvent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			int eventID = Integer.parseInt(request.getParameter("eventID"));
			List<Comment> list = commentDAO.getAllCommentbyEvent(eventID);
			System.out.println(list);
			System.out.println(eventID);
			request.setAttribute("commentList", list);
			request.getRequestDispatcher("/views/eventInfo.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void destroy() {
		commentDAO = null;
	}
}