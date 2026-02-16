package veigar.tools;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import veigar.exception.VeigarException;
import veigar.task.DeadlineTask;
import veigar.task.EventTask;
import veigar.task.Task;
import veigar.task.ToDoTask;

public class StorageTest {
    // Test empty file returns empty list
    @Test
    public void storageTest1() {
        Storage.setPath("data/empty.json");
        List<Task> output = Storage.load();
        List<Task> expected = new ArrayList<>();
        assertEquals(expected, output);
    }

    @Test
    public void saveAndLoadRoundtrip() throws VeigarException {
        Storage.setPath("data/test_storage.json");
        List<Task> tasks = new ArrayList<>();
        tasks.add(new ToDoTask("t1"));
        tasks.add(new DeadlineTask("d1", "27/3/2003"));
        tasks.add(new EventTask("e1", "27/3/2003 1200", "27/3/2003 1300"));

        Storage.save(tasks);

        List<Task> loaded = Storage.load();
        assertEquals(3, loaded.size());
        assertEquals("[T][ ]t1", loaded.get(0).toString());
        assertEquals("[D][ ]d1\n(by:27 Mar 2003, 0000)", loaded.get(1).toString());
        assertEquals("[E][ ]e1\n(from: 27 Mar 2003, 1200 to: 27 Mar 2003, 1300)", loaded.get(2).toString());
    }

    @Test
    public void loadMalformedReturnsEmpty() throws IOException {
        Storage.setPath("data/bad.json");

        List<Task> loaded = Storage.load();
        assertEquals(0, loaded.size());
    }
}
