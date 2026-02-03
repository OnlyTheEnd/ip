import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

public class Parser {


    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("d MMM yyyy, HHmm");

    private static final DateTimeFormatter FLEXIBLE_FORMATTER =
            new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .appendOptional(DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH))
                    .appendOptional(DateTimeFormatter.ofPattern("d/M/yyyy HHmm"))
                    .appendOptional(DateTimeFormatter.ofPattern("d MMM yyyy, h:mma"))
                    .appendOptional(DateTimeFormatter.ofPattern("d/M/yyyy"))
                    .appendOptional(DateTimeFormatter.ofPattern("E ha",Locale.ENGLISH)).toFormatter();

    public static String parseDateTime(String byString) {
        TemporalAccessor parsed =
                FLEXIBLE_FORMATTER.parse(byString);
        LocalDateTime date;
        LocalDateTime now = LocalDateTime.now();
        /* Case 1: Day of week → next day */
        if (parsed.isSupported(ChronoField.DAY_OF_WEEK)) {
            DayOfWeek target = DayOfWeek.of(parsed.get(ChronoField.DAY_OF_WEEK));
            date = now.with(TemporalAdjusters.next(target));
            /* Case 2: Day of week and time*/
            if (parsed.isSupported(ChronoField.HOUR_OF_DAY)) {
                date = LocalDateTime.of(LocalDate.from(date), LocalTime.from(parsed));
            }
        }

        /* Case 2: DateTime */
        else if (parsed.isSupported(ChronoField.HOUR_OF_DAY)) {
            date = LocalDateTime.from(parsed);
        }


        /* Case 3: Date only */
        else {
            date = LocalDate.from(parsed).atStartOfDay();
        }
        return date.format(OUTPUT_FORMAT);
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
