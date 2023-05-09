package net.slonka.greencode.atmservice.domain;

public class OrderTypeConverter {
    public static OrderType convert(String requestType) {
        return switch (requestType) {
            case "STANDARD" -> OrderType.STANDARD_REFILL;
            case "PRIORITY" -> OrderType.PRIORITY_REFILL;
            case "SIGNAL_LOW" -> OrderType.LOW_CASH_ALERT;
            case "FAILURE_RESTART" -> OrderType.EMERGENCY_REPAIR;
            default -> throw new IllegalArgumentException("Invalid request type");
        };
    }
}
