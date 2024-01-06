package org.example.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class DateParser {
    public static String formatDate(String dateString) {
        List<DateTimeFormatter> possibleFormats = Arrays.asList(
                DateTimeFormatter.ofPattern("M/d/yyyy"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy"),
                DateTimeFormatter.ofPattern("d/M/yyyy"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
                // Add more date formats as needed
        );

        for (DateTimeFormatter format : possibleFormats) {
            try {
                // Attempt to parse the date using each format
                LocalDate parsedDate = LocalDate.parse(dateString, format);
                // If successful, return the date in a standardized format
                return parsedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception ignored) {
                // Move to the next format if parsing fails
            }
        }

        // Return original string if none of the formats match
        return dateString;
    }
}
