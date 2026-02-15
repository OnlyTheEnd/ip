package veigar.tools;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


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
//    @Test
//    public void parseDateTimeTest3() {
//        String output = Parser.parseDateTime("Monday 4PM");
//        assertEquals(",1600", output);
//    }
    @Test
    public void parseDateTimeTest4() {
        String output = Parser.parseDateTime("27/3/2003");
        assertEquals("27 Mar 2003, 0000", output);
    }

    @Test
    public void parseDateTimeTest2() {
        String output = Parser.parseDateTime("2 Feb 2023, 5:00PM");
        assertEquals("2 Feb 2023, 1700", output);
    }


}
