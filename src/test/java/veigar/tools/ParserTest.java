package veigar.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import veigar.exception.VeigarException;
import veigar.task.Task;
import veigar.task.ToDoTask;



public class ParserTest {
    /**
     * Enum for possible user inputs.
     * 1. Date and Time with slashes ex. 31/12/2023 2359.
     * 2. Date and Time with letters ex. 2 Feb 2023, 5:00PM.
     * 3. Day and Time -> Next Occurrence of Day at set time ex. Monday 4pm.
     * 4. Date with slashes -> Date and time set to 0000 ex.31/12/2023.
     * 5. Day -> Next Occurrence of Day at current time.ex.Monday.
     *
     */
    protected static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("d MMM uuuu, HHmm");

    // Test Format 1: Date and Time with slashes (d/M/uuuu HHmm)
    @Test
    public void parseDateTimeSlashFormatValid() throws VeigarException {
        String output = Parser.parseDateTime("31/12/2023 2359").format(OUTPUT_FORMAT);
        assertEquals("31 Dec 2023, 2359", output);
    }

    @Test
    public void parseDateTimeSlashFormatMidday() throws VeigarException {
        String output = Parser.parseDateTime("15/6/2024 1200").format(OUTPUT_FORMAT);
        assertEquals("15 Jun 2024, 1200", output);
    }

    // Test Format 2: Date and Time with letters (d MMM uuuu, h:mma)
    @Test
    public void parseDateTimeTextFormatValid() throws VeigarException {
        String output = Parser.parseDateTime("2 Feb 2023, 5:00PM").format(OUTPUT_FORMAT);
        assertEquals("2 Feb 2023, 1700", output);
    }

    @Test
    public void parseDateTimeTextFormatMorning() throws VeigarException {
        String output = Parser.parseDateTime("14 Mar 2025, 9:30AM").format(OUTPUT_FORMAT);
        assertEquals("14 Mar 2025, 0930", output);
    }

    // Test Format 3: Day and Time (EEEE ha)
    @Test
    public void parseDayTimeFormatMonday() throws VeigarException {
        LocalDateTime result = Parser.parseDateTime("Monday 4PM");
        assertEquals("MONDAY", result.getDayOfWeek().toString());
        assertEquals(16, result.getHour());
    }

    @Test
    public void parseDayTimeFormatFriday() throws VeigarException {
        LocalDateTime result = Parser.parseDateTime("Friday 2PM");
        assertEquals("FRIDAY", result.getDayOfWeek().toString());
        assertEquals(14, result.getHour());
    }

    // Test Format 4: Date with slashes only (d/M/uuuu)
    @Test
    public void parseDateSlashFormatValid() throws VeigarException {
        String output = Parser.parseDateTime("27/3/2003").format(OUTPUT_FORMAT);
        assertEquals("27 Mar 2003, 0000", output);
    }

    @Test
    public void parseDateSlashFormatNewYear() throws VeigarException {
        String output = Parser.parseDateTime("1/1/2024").format(OUTPUT_FORMAT);
        assertEquals("1 Jan 2024, 0000", output);
    }

    // Test Format 5: Day only (EEEE)
    @Test
    public void parseDayOnlyFormatTuesday() throws VeigarException {
        LocalDateTime result = Parser.parseDateTime("Tuesday");
        assertEquals("TUESDAY", result.getDayOfWeek().toString());
    }

    @Test
    public void parseDayOnlyFormatSunday() throws VeigarException {
        LocalDateTime result = Parser.parseDateTime("Sunday");
        assertEquals("SUNDAY", result.getDayOfWeek().toString());
    }

    // Invalid format tests
    @Test
    public void parseInvalidDateFormatThrows() {
        assertThrows(VeigarException.class, () -> Parser.parseDateTime("not a date"));
    }
    @Test
    public void parseDateTimeInvalidThrows() {
        assertThrows(VeigarException.class, () -> Parser.parseDateTime("not a date"));
    }

    @Test
    public void parseIndexValidAndInvalid() throws VeigarException {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new ToDoTask("one"));
        tasks.add(new ToDoTask("two"));
        TaskList taskList = new TaskList(tasks);

        // valid
        int idx = Parser.parseIndex("1", taskList);
        assertEquals(0, idx);

        // non-numeric
        assertThrows(VeigarException.class, () -> Parser.parseIndex("a", taskList));

        // out of bounds
        assertThrows(VeigarException.class, () -> Parser.parseIndex("5", taskList));
    }
}
