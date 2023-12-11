package controller;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

import dal.AttendeeDAO;
import dal.EventDAO;
import dal.EventOrganizerDAO;
import dal.TicketDAO;
import dal.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Event;
import model.EventOrganizer;
import model.Ticket;
import model.User;
import util.TicketUtils;

@WebServlet(name = "EventServlet", urlPatterns = { "/EventServlet" })
public class EventServlet extends HttpServlet {
	private EventDAO eventDAO;
	private EventOrganizerDAO organizerDAO;
	private AttendeeDAO attendeeDAO;
	private UserDAO userDAO;

	@Override
	public void init() throws ServletException {
		super.init();
		eventDAO = new EventDAO();
		organizerDAO = new EventOrganizerDAO();
		attendeeDAO = new AttendeeDAO();
		userDAO = new UserDAO();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action != null)
		{
			switch (action) {
				case "registerEvent":
					registerEvent(request,response);
					break;
				case "createEvent":
					createEvent(request, response);
					break;
				case "deleteEvent":
					deleteEvent(request, response);
					break;
				case "editEvent":
					editEvent(request, response);
					break;
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
					break; // Add break statement
				case "getAllEvents":
					getAllEvents(request, response);
					break; // Add break statement
				case "getEventByName": // New case for getting event by name
					getEventByName(request, response);
					break;
				case "getAttendeeCountForEvent":
					getAttendeeCountForEvent(request, response);
				case "searchByCategory":
					searchByCategory(request, response);
					break;
				default:
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
			}
		} else {
			// Load the categories and events for the home page
			loadHomePage(request, response);
		}
	}

	private void deleteEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int eventid = Integer.parseInt(request.getParameter("eventId"));
		int sjsuid = Integer.parseInt(request.getParameter("sjsuId"));

		Event event = eventDAO.getEventById(eventid);
		EventOrganizer organizer = organizerDAO.getOrganizerById(sjsuid);


		if(eventDAO.deleteEvent(event, organizer) == 0)
		{
			String failed = "Deletion Failed. Please check your ID and eventID (you can only delete event that you created).";
			request.setAttribute("message", failed);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/deleteEvent.jsp");
			dispatcher.forward(request, response);
		}

		eventDAO.deleteEvent(event, organizer);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/home.jsp");
		dispatcher.forward(request, response);
	}

	private void createEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int organizerId = Integer.parseInt(request.getParameter("sjsuId"));
		String eventName = request.getParameter("eventName");
		String eventDateStr = request.getParameter("eventDate");
		String eventTimeStr = request.getParameter("eventTime");
		String eventDescription = request.getParameter("eventDescription");
		String eventCategory = request.getParameter("eventCategory");
		String requiresTicketStr = request.getParameter("requiresTicket");
		boolean requiresTicket = Boolean.parseBoolean(request.getParameter("requiresTicket"));
		LocalTime localTime = LocalTime.parse(eventTimeStr);
		Time sqlTime = Time.valueOf(localTime);
		java.sql.Date eventDateSql = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			if (eventDateStr != null && !eventDateStr.isEmpty()) {
				java.util.Date utilDate = dateFormat.parse(eventDateStr);
				eventDateSql = new java.sql.Date(utilDate.getTime());
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		Event event = new Event(eventName, eventDateSql, sqlTime, eventDescription,
				eventCategory, requiresTicket);
		EventOrganizer organizer = organizerDAO.getOrganizerById(organizerId);
		System.out.println(requiresTicketStr);
		System.out.println(requiresTicket);

		eventDAO.createEvent(event);
		int eventID = eventDAO.getEventIDbyName(eventName);
		event.setEventID(eventID);
		eventDAO.addDatatoManage(eventID, organizer);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/home.jsp");
		dispatcher.forward(request, response);
	}
	private void registerEvent(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));
		int eventId = Integer.parseInt(request.getParameter("eventId"));

		Event event = eventDAO.getEventById(eventId);
		User user = userDAO.getUserById(sjsuId);

		// Register the user for the event (in both cases)
		boolean registrationSuccessful = eventDAO.registerEvent(event, user);

		// Set the registration status as an attribute
		request.setAttribute("isUserRegistered", registrationSuccessful);

		// Check if the event requires ticketing
		if (event.isRequiresTicket()) {
			if (registrationSuccessful) {
				// Generate a unique barcode and create a ticket
				String ticketBarcode = TicketUtils.generateUniqueBarcode();
				// TicketId is auto-generated in the database, pass 0 or a similar placeholder
				Ticket ticket = new Ticket(0, eventId, ticketBarcode, sjsuId);
				TicketDAO ticketDAO = new TicketDAO();
				boolean ticketSaved = ticketDAO.createTicket(ticket);

				if (ticketSaved) {
					// Redirect to the attendee dashboard to view the ticket
					response.sendRedirect(request.getContextPath() + "/views/attendeeDash.jsp");
					return;
				} else {
					// Handle ticket saving failure
					// Set an attribute to indicate a duplicate ticket attempt
					request.setAttribute("errorMessage", "Oops! Something went wrong.");
				}
			} else {
				request.setAttribute("errorMessage", "Oops! Something went wrong.");
			}
		} else {
			if (!registrationSuccessful) {
				// User has already registered for this non-ticketed event
				request.setAttribute("errorMessage", "You have already registered for this event.");
			} else {
				// Redirect after successful registration without a ticket
				response.sendRedirect(request.getContextPath() + "/views/home.jsp");
				return;
			}
		}

		// Forward to eventInfo.jsp in all cases
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/home.jsp");
		dispatcher.forward(request, response);
	}

	private void getAttendeeCountForEvent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));

		EventDAO eventDAO = new EventDAO();
		HashMap<String, Integer> attendeeCount = eventDAO.getAttendeeCountForEvent(sjsuId);

		// Forward this data to a JSP page
		request.setAttribute("attendeeCount", attendeeCount);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/organizerDash.jsp");
		dispatcher.forward(request, response);
	}

	public void editEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int eventID = Integer.parseInt(request.getParameter("eventID"));
		String eventName = request.getParameter("eventName");
		String eventDateStr = request.getParameter("eventDate");
		String eventTimeStr = request.getParameter("eventTime");
		String description = request.getParameter("eventDescription");
		String category = request .getParameter("eventCategory");
		boolean requiresTicket = Boolean.parseBoolean(request.getParameter("requiresTicket"));
		LocalTime localTime = LocalTime.parse(eventTimeStr);
		Time sqlTime = Time.valueOf(localTime);
		java.sql.Date eventDateSql = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (eventDateStr != null && !eventDateStr.isEmpty()) {
				java.util.Date utilDate = dateFormat.parse(eventDateStr);
				eventDateSql = new java.sql.Date(utilDate.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Event event = eventDAO.getEventById(eventID);
		event.setEventName(eventName);
		event.setDate(eventDateSql);
		event.setTime(sqlTime);
		event.setDescription(description);
		event.setCategory(category);
		event.setRequiresTicket(requiresTicket);

		eventDAO.editEvent(event);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/home.jsp");
		dispatcher.forward(request, response);
	}

	private void searchByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String category = request.getParameter("category");
		List<Event> events = eventDAO.getEventsByCategory(category);
		request.setAttribute("events", events);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/home.jsp");
		dispatcher.forward(request, response);
	}

	// Method to load the home page
	private void loadHomePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Fetch the categories from the database using EventDAO
		List<String> categories = eventDAO.getAllEventCategories();
		request.setAttribute("categories", categories);

		// Fetch all events for the home page by default
		List<Event> events = eventDAO.getAllEvents();
		request.setAttribute("events", events);

		// Forward to the home.jsp with events and categories loaded
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/home.jsp");
		dispatcher.forward(request, response);
	}
	public void getEventByID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int eventID = Integer.parseInt(request.getParameter("eventID"));

		Event event = eventDAO.getEventById(eventID);

		request.setAttribute("event", event);
		request.getRequestDispatcher("/views/eventInfo.jsp").forward(request, response);

	}

	public void getAllEvents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<Event> list = eventDAO.getAllEvents();

		request.setAttribute("eventList", list);
		request.getRequestDispatcher("/views/home.jsp").forward(request, response);

	}

	private void getEventByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String eventName = request.getParameter("eventName");
		Event event = eventDAO.getEventByName(eventName);

		if (event != null) {
			request.setAttribute("event", event);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/attendeeDash.jsp");
			dispatcher.forward(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Event not found");
		}
	}

	@Override
	public void destroy() {
		eventDAO = null;
		organizerDAO = null;
	}
}
