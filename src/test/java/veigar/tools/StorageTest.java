package veigar.tools;

import org.junit.jupiter.api.Test;
import veigar.task.Task;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {
    //Test empty file returns empty list
    @Test
    public void StorageTest1() {
        List<Task> output = Storage.load();
        List<Task> expected = new ArrayList<>();
        assertEquals(output, expected);
    }
}
