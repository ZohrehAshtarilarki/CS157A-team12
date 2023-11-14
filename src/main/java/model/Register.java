package model;

public class Register {
    private String sjsuId;
    private String eventId;
    private boolean isCheckIn;

    public Register(String sjsuId, String eventId, boolean isCheckIn) {
        this.sjsuId = sjsuId;
        this.eventId = eventId;
        this.isCheckIn = isCheckIn;
    }

    public String getSjsuId() {
        return sjsuId;
    }

    public void setSjsuId(String sjsuId) {
        this.sjsuId = sjsuId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public boolean isCheckIn() {
        return isCheckIn;
    }

    public void setCheckIn(boolean checkIn) {
        isCheckIn = checkIn;
    }
}
