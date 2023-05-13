package net.slonka.greencode.atmservice.solver;

import net.slonka.greencode.atmservice.domain.ATM;
import net.slonka.greencode.atmservice.domain.Task;

import java.util.*;

public class ConvoyOrderSystem {
    private static Comparator<Task> comparator = new TaskComparator();

    public static List<ATM> calculateOrder(List<Task> tasks) {
        var sorted = new ArrayList<>(tasks);
        sorted.sort(comparator);
        var seenTasks = new HashSet<>(tasks.size());
        var finalResult = new ArrayList<ATM>(tasks.size());

        for(var task : sorted) {
            var atm = new ATM(task.getRegion(), task.getAtmId());
            if (!seenTasks.contains(atm)) {
                seenTasks.add(atm);
                finalResult.add(atm);
            }
        }

        return finalResult;
    }
}

