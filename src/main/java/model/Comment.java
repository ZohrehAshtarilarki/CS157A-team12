package model;

public class Comment {
	private int commentID;
	private int eventID;
	private int sjsuID;
	private String commentText;
	
	public Comment(int eventID, int sjsuID, String commentText)
	{
		this.eventID = eventID;
		this.sjsuID = sjsuID;
		this.commentText = commentText;
	}

	public Comment() {
		super();
	}

	
	public int getCommentID() {
		return commentID;
	}

	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public int getSjsuID() {
		return sjsuID;
	}

	public void setSjsuID(int sjsuID) {
		this.sjsuID = sjsuID;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	
	
}