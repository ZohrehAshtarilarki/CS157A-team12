package model;

public class Ticket {
    private String ticketId;
    private String eventId;
    private String ticketBarcode;

    public Ticket(String ticketId, String eventId, String ticketBarcode) {
        this.ticketId = ticketId;
        this.eventId = eventId;
        this.ticketBarcode = ticketBarcode;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTicketBarcode() {
        return ticketBarcode;
    }

    public void setTicketBarcode(String ticketBarcode) {
        this.ticketBarcode = ticketBarcode;
    }
}

