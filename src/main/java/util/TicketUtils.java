package util;

import java.util.UUID;

public class TicketUtils {

    // Method for generating a unique barcode
    public static String generateUniqueBarcode() {
        return UUID.randomUUID().toString();
    }

    // Method for generating a unique ticket ID
    public static String generateUniqueTicketId() {
        // UUID to generate a unique identifier
        return UUID.randomUUID().toString();
    }
}
