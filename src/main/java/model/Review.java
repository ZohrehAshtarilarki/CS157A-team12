package model;

public class Review {
    private int eventId; // Foreign key to link with Event entity
    private int sjsuId; // Foreign key to link with Attendee entity (sjsuId)
    private String reviewText;
    private float rating;
    private int reviewId;

    // Default constructor
    public Review() {}

    public Review(int reviewId) {this.reviewId = reviewId;}

    // Parameterized constructor
    public Review(int eventId, int sjsuId, String reviewText, float rating) {
        this.eventId = eventId;
        this.sjsuId = sjsuId;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    // Getters and setters

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getSjsuId() {
        return sjsuId;
    }

    public void setSjsuId(int attendeeId) {
        this.sjsuId = attendeeId;
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

    public int getReviewId() {return reviewId;}

    public void setReviewId(int reviewId) {this.reviewId = reviewId;}

    // Override toString() method for easy printing
    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                "rating=" + rating +
                ", eventId=" + eventId +
                ", sjsuId=" + sjsuId +
                ", reviewText='" + reviewText + '\'' +
                ", rating=" + rating +
                '}';
    }
}
