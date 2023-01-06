package com.kenzie.appserver.service.model;

import java.util.Objects;

public class Car {
    private final String id;
    private final String make;
    private final String model;
    private final int year;
    private boolean isAvailable;
    private String dateRented;
    private String returnDate;

    public Car(String id, String make, String model, int year, boolean isAvailable, String dateRented,
               String returnDate) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.isAvailable = isAvailable;
        this.dateRented = dateRented;
        this.returnDate = returnDate;
    }

    public String getId() {
        return id;
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

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public String getDateRented() {
        return dateRented;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setDateRented(String dateRented) {
        this.dateRented = dateRented;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return year == car.year && isAvailable == car.isAvailable && Objects.equals(id, car.id) && Objects.equals(make, car.make) && Objects.equals(model, car.model) && Objects.equals(dateRented, car.dateRented) && Objects.equals(returnDate, car.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, make, model, year, isAvailable, dateRented, returnDate);
    }
}
