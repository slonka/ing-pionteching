package net.slonka.greencode.atmservice.domain;

import java.util.Objects;

public class Order {
    private int region;
    private int atmId;

    private OrderType orderType;

    public Order(int region, int atmId, OrderType orderType) {
        this.region = region;
        this.atmId = atmId;
        this.orderType = orderType;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getAtmId() {
        return atmId;
    }

    public void setAtmId(int atmId) {
        this.atmId = atmId;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return region == order.region && atmId == order.atmId && orderType == order.orderType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, atmId, orderType);
    }
}

