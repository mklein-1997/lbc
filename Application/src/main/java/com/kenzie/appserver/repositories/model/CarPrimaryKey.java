package com.kenzie.appserver.repositories.model;

import java.util.Objects;

public class CarPrimaryKey {

    private final String trackingId;

    public CarPrimaryKey(String trackingId) {
        this.trackingId = trackingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarPrimaryKey that = (CarPrimaryKey) o;
        return Objects.equals(trackingId, that.trackingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingId);
    }
}
