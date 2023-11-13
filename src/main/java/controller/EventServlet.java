package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import dal.AttendeeDAO;
import dal.EventDAO;
import dal.EventOrganizerDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Attendee;
import model.Event;
import model.EventOrganizer;

@WebServlet(name = "EventServlet", urlPatterns = { "/EventServlet" })
public class EventServlet extends HttpServlet {
	private EventDAO eventDAO;
	private EventOrganizerDAO organizerDAO;
	private AttendeeDAO attendeeDAO;
	
	@Override
	public void init() throws ServletException {
		super.init();
		eventDAO = new EventDAO();
		organizerDAO = new EventOrganizerDAO();
		attendeeDAO = new AttendeeDAO();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
	
		if (action != null)
		{
			switch (action) {
			case "registerEvent":
				registerEvent(request,response);
			case "createEvent":
				createEvent(request, response);
/*
			case "delete":
				deleteEvent(request, response);
*/
			default:
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
			}	
		}
	}
	
	private void createEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int eventId = Integer.parseInt(request.getParameter("eventid"));
        int organizerId = Integer.parseInt(request.getParameter("sjsuid"));
        String eventName = request.getParameter("eventname");
        String eventDateStr = request.getParameter("eventdate");
        String eventTimeStr = request.getParameter("eventtime");
        String eventDescription = request.getParameter("eventdescription");
        String eventCategory = request.getParameter("eventcategory");

        Date eventDate = null;
        Time eventTime = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        try {
            if (eventDateStr != null && !eventDateStr.isEmpty()) {
                java.util.Date utilDate = dateFormat.parse(eventDateStr);
                eventDate = new Date(utilDate.getTime());
            }

            if (eventTimeStr != null && !eventTimeStr.isEmpty()) {
                java.util.Date utilTime = timeFormat.parse(eventTimeStr);
                eventTime = new Time(utilTime.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Event ID: " + eventId);
        System.out.println("Organizer ID: " + organizerId);
        System.out.println("Event Name: " + eventName);
        System.out.println("Event Date: " + eventDate);
        System.out.println("Event Time: " + eventTime);
        System.out.println("Event Description: " + eventDescription);
        System.out.println("Event Category: " + eventCategory);

        Event event = new Event(eventId,eventName,eventDate,eventTime,eventDescription,eventCategory);
        EventOrganizer organizer = organizerDAO.getOrganizerById(organizerId);
        
        eventDAO.createEvent(event, organizer);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/home.jsp");
	    dispatcher.forward(request, response);
        }

	private void registerEvent(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			int sjsuid = Integer.parseInt(request.getParameter("sjsuid"));
			int eventid = Integer.parseInt(request.getParameter("eventid"));
			
			Event event = eventDAO.getEventById(eventid);
			Attendee attendee = attendeeDAO.getAttendeeById(sjsuid);
			
			System.out.println(event);
			System.out.println(attendee);
			
			eventDAO.registerEvent(event, attendee);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/views/home.jsp");
		    dispatcher.forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if (action != null)
		{
			switch (action) {
			case "getEventByID":
				getEventByID(request, response);
				break; // Add break statement
			case "getAllEvents":
				getAllEvents(request, response);
				break; // Add break statement
			default:
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
			}
		}
	}
/*

	public void createEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
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
	*/
	public void getEventByID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int eventID = Integer.parseInt(request.getParameter("eventID"));
		
		Event event = eventDAO.getEventById(eventID);
		
        request.setAttribute("event", event);
        request.getRequestDispatcher("eventDetails.jsp").forward(request, response);
		
	}

	public void getAllEvents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<Event> list = eventDAO.getAllEvents();
		
        request.setAttribute("eventList", list);
        request.getRequestDispatcher("/views/home.jsp").forward(request, response);
		
	}
	
	@Override
	public void destroy() {
		eventDAO = null;
		organizerDAO = null;
	}
}
