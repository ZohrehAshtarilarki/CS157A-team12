package model;

import java.sql.Date;
import java.sql.Time;

public class Event{
	private int eventId;
	private String eventName;
	private Date date;
	private Time time;
	private String description;
	private String category;
	private boolean requiresTicket;

	public Event() {}

	public Event(int eventId, String eventName, Date date, Time time, String description, String category,
				 boolean requiresTicket)
	{
		this.eventId = eventId;
		this.eventName = eventName;
		this.date = date;
		this.time = time;
		this.description = description;
		this.category = category;
		this.requiresTicket = requiresTicket;
	}

	public int getEventID() {
		return eventId;
	}
	public void setEventID(int eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public boolean isRequiresTicket() { return requiresTicket; }
	public void setRequiresTicket(boolean requiresTicket) { this.requiresTicket = requiresTicket; }
}