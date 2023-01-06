package com.kenzie.appserver.service.model;

public class Car {
    private final String id;
    private final String make;
    private final String model;
    private final int year;
    private final boolean isAvailable;
    private final String dateRented;
    private final String returnDate;

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
}
