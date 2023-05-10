package net.slonka.greencode.atmservice.domain;

import java.util.Objects;

public class Task {
    private int region;
    private String requestType;
    private int atmId;

    public Task(int region, String requestType, int atmId) {
        this.region = region;
        this.requestType = requestType;
        this.atmId = atmId;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public int getAtmId() {
        return atmId;
    }

    public void setAtmId(int atmId) {
        this.atmId = atmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return region == task.region && atmId == task.atmId && Objects.equals(requestType, task.requestType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, requestType, atmId);
    }
}
