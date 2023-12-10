package model;

public class Notification{
    private int notificationID;
    private int eventID;
    private String notificationText;

    public Notification(int eventID, String notificationText)
    {
        this.eventID = eventID;
        this.notificationText = notificationText;
    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }


}