package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class CarUpdateRequest {

    @NotEmpty
    @JsonProperty("trackingId")
    private String trackingId;

    @NotEmpty
    @JsonProperty("make")
    private String make;

    @NotEmpty
    @JsonProperty("model")
    private String model;

    @JsonProperty("isAvailable")
    private boolean isAvailable;

    @Min(1000)
    @JsonProperty("year")
    int year;

    @NotEmpty
    @JsonProperty("dateRented")
    private String dateRented;

    @NotEmpty
    @JsonProperty("returnDate")
    private String returnDate;

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
}
