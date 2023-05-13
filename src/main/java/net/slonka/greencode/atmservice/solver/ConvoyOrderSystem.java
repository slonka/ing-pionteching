package net.slonka.greencode.atmservice.solver;

import net.slonka.greencode.atmservice.domain.ATM;
import net.slonka.greencode.atmservice.domain.Task;

import java.util.*;

public class ConvoyOrderSystem {
    private static Comparator<Task> comparator = new TaskComparator();

    public static List<ATM> calculateOrder(Task[] tasks) {
        Arrays.sort(tasks);
        var seenTasks = new HashSet<>(tasks.length);
        var finalResult = new ArrayList<ATM>(tasks.length);

        for(var task : tasks) {
            var atm = new ATM(task.getRegion(), task.getAtmId());
            if (!seenTasks.contains(atm)) {
                seenTasks.add(atm);
                finalResult.add(atm);
            }
        }

        return finalResult;
    }
}

