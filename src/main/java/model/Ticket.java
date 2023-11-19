package model;

public class Ticket {
    private int ticketId;
    private int eventId;
    private String ticketBarcode;

    // New field for storing the user's ID since an attendee should register for the same event only one time
    private int sjsuId;

    public Ticket(int ticketId, int eventId, String ticketBarcode, int sjsuId) {
        this.ticketId = ticketId;
        this.eventId = eventId;
        this.ticketBarcode = ticketBarcode;
        this.sjsuId = sjsuId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTicketBarcode() {
        return ticketBarcode;
    }

    public void setTicketBarcode(String ticketBarcode) {
        this.ticketBarcode = ticketBarcode;
    }

    public int getSjsuId() {
        return sjsuId;
    }

    public void setSjsuId(int sjsuId) {
        this.sjsuId = sjsuId;
    }
}

