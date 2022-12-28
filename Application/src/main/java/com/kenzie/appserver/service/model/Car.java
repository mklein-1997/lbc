package com.kenzie.appserver.service.model;

import java.util.Objects;
import java.util.UUID;

import static java.util.UUID.randomUUID;

public class Car {
    private String trackingId;
    private final String make;
    private final String model;
    private final int year;
    private Boolean isAvailable;
    private String dateRented;
    private String returnDate;

    public Car(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.trackingId = randomUUID().toString();
        this.isAvailable = true;
        this.dateRented = null;
        this.returnDate = null;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getDateRented() {
        return dateRented;
    }

    public void setDateRented(String dateRented) {
        this.dateRented = dateRented;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return year == car.year && Objects.equals(trackingId, car.trackingId) && Objects.equals(make, car.make) && Objects.equals(model, car.model) && Objects.equals(isAvailable, car.isAvailable) && Objects.equals(dateRented, car.dateRented) && Objects.equals(returnDate, car.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingId, make, model, year, isAvailable, dateRented, returnDate);
    }
}
