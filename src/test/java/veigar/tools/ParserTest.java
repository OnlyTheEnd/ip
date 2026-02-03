package veigar.tools;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void parseDateTimeTest1(){
        String output = Parser.parseDateTime("27/3/2003");
        assertEquals("27 Mar 2003, 0000", output);
    }

    @Test
    public void parseDateTimeTest2(){
        String output = Parser.parseDateTime("2 Feb 2023, 5:00PM");
        assertEquals("2 Feb 2023, 1700", output);
    }

}
