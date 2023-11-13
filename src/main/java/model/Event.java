package model;

import java.sql.Date;
import java.sql.Time;

public class Event{
	private int eventID;
	private String eventName;
	private Date date;
	private Time time;
	private String description;
	private String category;
	
	public Event() {}
	
	public Event(int eventID, String eventName, Date date, Time time, String description, String category)
	{
		this.eventID = eventID;
		this.eventName = eventName;
		this.date = date;
		this.time = time;
		this.description = description;
		this.category = category;
	}
	
	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
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
}