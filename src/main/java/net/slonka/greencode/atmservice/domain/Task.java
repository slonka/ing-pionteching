package net.slonka.greencode.atmservice.domain;

import java.util.Objects;

public class Task {
    public int region;
    public RequestType requestType;
    public int atmId;

    public Task(int region, RequestType requestType, int atmId) {
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

    public int getAtmId() {
        return atmId;
    }

    public void setAtmId(int atmId) {
        this.atmId = atmId;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return region == task.region && atmId == task.atmId && requestType == task.requestType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, requestType, atmId);
    }
}
