package net.slonka.greencode.atmservice.solver;

import net.slonka.greencode.atmservice.domain.Order;
import net.slonka.greencode.atmservice.domain.OrderType;
import net.slonka.greencode.atmservice.domain.OrderTypeConverter;
import net.slonka.greencode.atmservice.domain.Task;

import java.util.ArrayList;
import java.util.List;

public class ConvoyOrderSystem {
    public static List<Order> calculateOrder(List<Task> tasks) {
        List<Order> orders = new ArrayList<>();

        for (Task task : tasks) {
            OrderType type = OrderTypeConverter.convert(task.getRequestType());
            orders.add(new Order(task.getRegion(), task.getAtmId(), type));
        }

        orders.sort(new OrderComparator());
        return orders;
    }
}

