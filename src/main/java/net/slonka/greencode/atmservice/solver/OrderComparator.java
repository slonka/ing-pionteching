package net.slonka.greencode.atmservice.solver;


import net.slonka.greencode.atmservice.domain.Order;

import java.util.Comparator;

class OrderComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        if (o1.getRegion() != o2.getRegion()) {
            return Integer.compare(o1.getRegion(), o2.getRegion());
        }

        if (o1.getOrderType() != o2.getOrderType()) {
            return o1.getOrderType().ordinal() - o2.getOrderType().ordinal();
        }

        return Integer.compare(o1.getAtmId(), o2.getAtmId());
    }
}
