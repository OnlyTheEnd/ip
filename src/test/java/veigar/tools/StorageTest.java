package veigar.tools;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import veigar.task.Task;

public class StorageTest {
    //Test empty file returns empty list
    @Test
    public void storageTest1() {
        List<Task> output = Storage.load();
        List<Task> expected = new ArrayList<>();
        assertEquals(output, expected);
    }
}
