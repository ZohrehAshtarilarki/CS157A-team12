package model;

public class Attendee extends User{
	private int attendeeId;

	public Attendee () {}
	// Constructor
	public Attendee(int sjsuId, String sjsuEmail, String username, String password, String role) {
		// To call the constructor of the User class for initializing inherited fields.
		super(sjsuId, sjsuEmail, username, password, role);
	}
}
