package model;

public class Review {
    private int reviewId;
    private int eventId; // Foreign key to link with Event entity
    private int attendeeId; // Foreign key to link with Attendee entity
    private String reviewText;
    private float rating;

    // Default constructor
    public Review() {}

    // Parameterized constructor
    public Review(int eventId, int attendeeId, String reviewText, float rating) {
        this.eventId = eventId;
        this.attendeeId = attendeeId;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    // Getters and setters
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getAttendeeId() {
        return attendeeId;
    }

    public void setAttendeeId(int attendeeId) {
        this.attendeeId = attendeeId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
