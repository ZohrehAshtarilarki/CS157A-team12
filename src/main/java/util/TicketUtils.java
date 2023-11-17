package util;

import java.util.UUID;

// utility function for generating a unique identifier for a ticket
public class TicketUtils {
    public static String generateUniqueBarcode() {
        // Generate a random UUID (Universally Unique Identifier)
        return UUID.randomUUID().toString();
    }
}