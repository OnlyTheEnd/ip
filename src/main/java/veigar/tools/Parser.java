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

public class Parser {

    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("d MMM uuuu, HHmm");

    //formats: 1.Monday -> next monday same time 2.31/12/2023 2359 3. 2 Feb 2023, 5:00PM 4.31/12/2003 5.Mon 4pm -> next monday time

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

    public static String parseDateTime(String byString) throws DateTimeParseException {
        try {
            LocalDateTime date = LocalDateTime.now();
            TemporalAccessor parsed = tryParse(byString);
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
        } catch (DateTimeParseException e) {
            System.out.println("Wrong date format");
        }
        return null;
    }

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
