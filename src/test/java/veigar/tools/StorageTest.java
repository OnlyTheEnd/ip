package veigar.tools;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.ArrayList;

import veigar.task.Task;



public class StorageTest {
    //Test empty file returns empty list
    @Test
    public void StorageTest1() {
        List<Task> output = Storage.load();
        List<Task> expected = new ArrayList<>();
        assertEquals(output, expected);
    }
}
