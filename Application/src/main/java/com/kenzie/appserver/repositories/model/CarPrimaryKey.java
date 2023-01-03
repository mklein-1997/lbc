package com.kenzie.appserver.repositories.model;

import java.util.Objects;

public class CarPrimaryKey {

    private final String id;

    public CarPrimaryKey(String trackingId) {
        this.id = trackingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarPrimaryKey that = (CarPrimaryKey) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
