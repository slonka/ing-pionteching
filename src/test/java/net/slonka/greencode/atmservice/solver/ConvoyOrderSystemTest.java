package net.slonka.greencode.atmservice.solver;

import net.slonka.greencode.atmservice.domain.ATM;
import net.slonka.greencode.atmservice.domain.RequestType;
import net.slonka.greencode.atmservice.domain.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConvoyOrderSystemTest {

    @Test
    void testCalculateOrderEmptyTasks() {
        Task[] tasks = {};
        List<ATM> result = ConvoyOrderSystem.calculateOrder(tasks);

        assertEquals(0, result.size());
    }

    @Test
    void testCalculateOrderSingleTask() {
        Task[] tasks = { new Task(1, RequestType.STANDARD, 100) };
        List<ATM> result = ConvoyOrderSystem.calculateOrder(tasks);

        assertEquals(1, result.size());
        assertEquals(new ATM(1, 100), result.get(0));
    }

    @Test
    void testCalculateOrderMultipleTasks() {
        Task[] tasks = {
                new Task(1, RequestType.PRIORITY, 100),
                new Task(2, RequestType.FAILURE_RESTART, 200),
                new Task(1, RequestType.STANDARD, 101),
                new Task(3, RequestType.SIGNAL_LOW, 300),
                new Task(1, RequestType.PRIORITY, 102)
        };

        List<ATM> result = ConvoyOrderSystem.calculateOrder(tasks);

        assertEquals(5, result.size());
        assertEquals(new ATM(1, 100), result.get(0));
        assertEquals(new ATM(1, 102), result.get(1));
        assertEquals(new ATM(1, 101), result.get(2));
        assertEquals(new ATM(2, 200), result.get(3));
        assertEquals(new ATM(3, 300), result.get(4));
    }

    @Test
    void testCalculateOrderDuplicateTasks() {
        Task[] tasks = {
                new Task(1, RequestType.PRIORITY, 100),
                new Task(1, RequestType.STANDARD, 100),
                new Task(2, RequestType.FAILURE_RESTART, 200),
                new Task(2, RequestType.FAILURE_RESTART, 200)
        };

        List<ATM> result = ConvoyOrderSystem.calculateOrder(tasks);

        assertEquals(2, result.size());
        assertEquals(new ATM(1, 100), result.get(0));
        assertEquals(new ATM(2, 200), result.get(1));
    }
}
