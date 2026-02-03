package veigar.tools;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import veigar.exception.VeigarException;

/**
 * Responsible for Parsing and reading user input into the app.
 */
public class Parser {

    /**
     * Standardises date and time output as for example 27 Mar 2003, 2200.
     */
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("d MMM uuuu, HHmm");

    /**
     * Enum for possible user inputs.
     * 1. Date and Time with slashes ex. 31/12/2023 2359.
     * 2. Date and Time with letters ex. 2 Feb 2023, 5:00PM.
     * 3. Day and Time -> Next Occurrence of Day at set time ex. Mon 4pm.
     * 4. Date with slashes -> Date and time set to 0000 ex.31/12/2023.
     * 5. Day -> Next Occurrence of Day at current time.ex.Monday.
     *
     */
    private enum DateFormat {
        DATETIME_SLASH("d/M/uuuu HHmm"),
        DATETIME_TEXT("d MMM uuuu, h:mma"),
        DAY_TIME("E ha"),
        DATE_SLASH("d/M/uuuu"),
        DAY_ONLY("EEEE");

        private final DateTimeFormatter formatter;
        DateFormat(String pattern) {
            this.formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
        }
        private DateTimeFormatter getFormatter() {
            return formatter;
        }
    }

    /**
     * Helper function for parseDateTime.

     * @param input User input of date as a String.
     * @return TemporalAccessor of String else null.
     */
    private static TemporalAccessor tryParse(String input) {
        for (DateFormat format : DateFormat.values()) {
            try {
                return format.getFormatter().parse(input);
            } catch (DateTimeParseException e) {
                // Not this format; try next
            }
        }
        return null;
    }

    /**
     * Compares between the DateFormat enums and return most specific match.
     * @param inputString User input of date as a String.
     * @return Date and time in the specified OUTPUT_FORMAT.
     * @throws NullPointerException if String pattern.
     */
    public static String parseDateTime(String inputString) throws NullPointerException {
        try {
            LocalDateTime date = LocalDateTime.now();
            TemporalAccessor parsed = tryParse(inputString);
            LocalDateTime now = LocalDateTime.now();
            if (parsed.isSupported(ChronoField.EPOCH_DAY)) {
                if (parsed.isSupported(ChronoField.HOUR_OF_DAY)) {
                    date = LocalDateTime.from(parsed);
                } else {
                    date = LocalDate.from(parsed).atStartOfDay();
                }
            } else if (parsed.isSupported(ChronoField.DAY_OF_WEEK)) {
                DayOfWeek target = DayOfWeek.of(parsed.get(ChronoField.DAY_OF_WEEK));
                date = now.with(TemporalAdjusters.next(target));
                if (parsed.isSupported(ChronoField.HOUR_OF_DAY)) {
                    date = LocalDateTime.of(date.toLocalDate(), LocalTime.from(parsed));
                }
            }
            return date.format(OUTPUT_FORMAT);
        } catch (NullPointerException d) {
            System.out.println("Wrong date format");
        }
        return null;
    }

    /**
     * Parses list index number from user input.
     * @param s Input Argument.
     * @param taskList List of Tasks.
     * @return A valid List index in taskList.
     * @throws VeigarException Number cannot be read or out of bounds
     */
    public static int parseIndex(String s, TaskList taskList) throws VeigarException {
        try {
            int n = Integer.parseInt(s) - 1;
            if (n < 0 || n >= taskList.getListSize()) {
                throw new VeigarException("Your commands are wrong");
            }
            return n;
        } catch (NumberFormatException e) {
            throw new VeigarException("Please provide a valid number");
        }
    }
}
