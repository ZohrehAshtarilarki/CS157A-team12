package model;

public class EventOrganizer extends User{

	private String organizationName;

	public EventOrganizer() {}
	public EventOrganizer(int sjsuId, String sjsuEmail, String username, String password, String role, String organizationName) {
		// To call the constructor of the User class for initializing inherited fields.
		super(sjsuId, sjsuEmail, username, password, role);
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
}
