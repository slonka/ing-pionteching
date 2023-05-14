package net.slonka.greencode.atmservice.solver;


import net.slonka.greencode.atmservice.domain.Task;

import java.util.Comparator;

class TaskComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        if (o1.getRegion() != o2.getRegion()) {
            return Integer.compare(o1.getRegion(), o2.getRegion());
        }

        if (o1.getRequestType() != o2.getRequestType()) {
            return o1.getRequestType().ordinal() - o2.getRequestType().ordinal();
        }

        return 0;
    }
}
