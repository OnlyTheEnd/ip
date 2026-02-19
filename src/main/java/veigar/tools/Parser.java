package veigar.tools;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
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
     * Enum for possible user date inputs.
     * 1. Date and Time with slashes ex. 31/12/2023 2359.
     * 2. Date and Time with letters ex. 2 Feb 2023, 5:00PM.
     * 3. Day and Time -> Next Occurrence of Day at set time ex. Monday 4PM.
     * 4. Date with letters ex. 26 Feb 2026
     * 5. Date with slashes -> Date and time set to 0000 ex.31/12/2023.
     * 6. Day -> Next Occurrence of Day at current time.ex. Monday.
     *
     */
    private enum DateFormat {
        DATETIME_SLASH("d/M/uuuu HHmm"),
        DATETIME_TEXT("d MMM uuuu, h:mma"),
        DAY_TIME("EEEE ha"),
        DATE("d MMM uuuu"),
        DATE_SLASH("d/M/uuuu"),
        DAY_ONLY("EEEE");

        private final DateTimeFormatter formatter;
        DateFormat(String pattern) {
            this.formatter = new DateTimeFormatterBuilder()
                                .parseCaseInsensitive()
                                .appendPattern(pattern)
                                .toFormatter(Locale.ENGLISH);
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
     * @return LocalDateTime.
     * @throws VeigarException if String pattern does not match.
     */
    public static LocalDateTime parseDateTime(String inputString) throws VeigarException {
        try {
            LocalDateTime date = LocalDateTime.now(); //default date is now
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
            return date;
        } catch (NullPointerException d) {
            throw new VeigarException("Your date is WRONG format");
        }
    }

    /**
     * Parses list index number from user input.
     * @param s Input Argument.
     * @param taskList List of Tasks.
     * @return A valid List index in taskList.
     * @throws VeigarException Number cannot be read or out of bounds
     */
    public static int parseIndex(String s, TaskList taskList) throws VeigarException {
        assert s != null;
        try {
            int n = Integer.parseInt(s) - 1;
            if (n < 0 || n >= taskList.getListSize()) {
                throw new VeigarException("Your index is wrong, Choose between 1 and " + taskList.getListSize());
            }
            return n;
        } catch (NumberFormatException e) {
            throw new VeigarException("Please provide a valid number");
        }
    }
}
