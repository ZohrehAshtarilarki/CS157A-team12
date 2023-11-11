package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import dal.EventDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Event;
import model.EventOrganizer;
import dal.EventOrganizerDAO;

public class EventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	private EventDAO eventDAO;
	private EventOrganizerDAO organizerDAO;
	
	@Override
	public void init() throws ServletException {
		super.init();
		eventDAO = new EventDAO();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
	
		if (action != null)
		{
			switch (action) {
			case "create":
				createEvent(request,response);
			case "edit":
				editEvent(request, response);
			case "delete":
				deleteEvent(request, response);
			default:
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
			}	
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if (action != null)
		{
			switch (action) {
			case "getEventByID":
				getEventByID(request, response);
			case "getAllEvents":
				getAllEvents(request, response);
			default:
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
			}
		}
	}
	
	
	public void createEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int eventID = Integer.parseInt(request.getParameter("eventID"));
		String eventName = request.getParameter("eventName");
		Date date = Date.valueOf(request.getParameter("date"));
		Time time = Time.valueOf(request.getParameter("time"));
		String description = request.getParameter("description");
		String category = request .getParameter("category");
		
		int SJSUID = Integer.parseInt(request.getParameter("SJSUID"));
		
		Event event = new Event(eventID, eventName, date, time, description, category);
		EventOrganizer eventOrganizer = organizerDAO.getOrganizerById(SJSUID);
		
		eventDAO.createEvent(event, eventOrganizer);
		
		response.sendRedirect("success.jsp");
	}
	
	public void editEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int eventID = Integer.parseInt(request.getParameter("eventID"));
		String eventName = request.getParameter("eventName");
		Date date = Date.valueOf(request.getParameter("date"));
		Time time = Time.valueOf(request.getParameter("time"));
		String description = request.getParameter("description");
		String category = request .getParameter("category");
		
		int SJSUID = Integer.parseInt(request.getParameter("SJSUID"));
		
		Event event = new Event(eventID, eventName, date, time, description, category);
		EventOrganizer eventOrganizer = organizerDAO.getOrganizerById(SJSUID);
		
		eventDAO.editEvent(event, eventOrganizer);
		
		response.sendRedirect("success.jsp");
	}
	
	public void deleteEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int eventID = Integer.parseInt(request.getParameter("eventID"));
		String eventName = request.getParameter("eventName");
		Date date = Date.valueOf(request.getParameter("date"));
		Time time = Time.valueOf(request.getParameter("time"));
		String description = request.getParameter("description");
		String category = request .getParameter("category");
		
		int SJSUID = Integer.parseInt(request.getParameter("SJSUID"));
		
		Event event = new Event(eventID, eventName, date, time, description, category);
		EventOrganizer eventOrganizer = organizerDAO.getOrganizerById(SJSUID);
		
		eventDAO.deleteEvent(event, eventOrganizer);
		
		response.sendRedirect("success.jsp");
	}
	
	public void getEventByID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int eventID = Integer.parseInt(request.getParameter("eventID"));
		
		Event event = eventDAO.getEventById(eventID);
		
        request.setAttribute("event", event);
        request.getRequestDispatcher("eventDetails.jsp").forward(request, response);
		
	}
	
	public void getAllEvents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<Event> list = eventDAO.getAllEvents();
		
        request.setAttribute("eventList", list);
        request.getRequestDispatcher("eventList.jsp").forward(request, response);
		
	}
	
	@Override
	public void destroy() {
		eventDAO = null;
		organizerDAO = null;
	}
}
