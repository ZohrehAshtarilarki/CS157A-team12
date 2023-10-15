package model;

public class User {
    private int sjsuId;
    private String sjsuEmail;
    private String username;
    private String password;
    
    // Default constructor
    public User() {}
    
    // Parameterized constructor
    public User(int sjsuId, String sjsuEmail, String username, String password) {
        this.sjsuId = sjsuId;
        this.sjsuEmail = sjsuEmail;
        this.username = username;
        this.password = password;
    }

    // Getters and setters for all fields
    public int getSjsuId() {
        return sjsuId;
    }

    public void setSjsuId(int sjsuId) {
        this.sjsuId = sjsuId;
    }

    public String getSjsuEmail() {
        return sjsuEmail;
    }

    public void setSjsuEmail(String sjsuEmail) {
        this.sjsuEmail = sjsuEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
