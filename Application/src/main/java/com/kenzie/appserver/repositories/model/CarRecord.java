package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "Cars")
public class CarRecord {

    private String trackingId;
    private String make;
    private String model;
    private int year;
    private Boolean isAvailable;
    private String dateRented;
    private String returnDate;

    @DynamoDBHashKey(attributeName = "trackingId")
    public String getTrackingId() {
        return trackingId;
    }

    @DynamoDBAttribute(attributeName = "make")
    public String getMake() {
        return make;
    }

    @DynamoDBAttribute(attributeName = "model")
    public String getModel() {
        return model;
    }

    @DynamoDBAttribute(attributeName = "year")
    public int getYear() {
        return year;
    }

    @DynamoDBAttribute(attributeName = "isAvailable")
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    @DynamoDBAttribute(attributeName = "dateRented")
    public String getDateRented() {
        return dateRented;
    }

    @DynamoDBAttribute(attributeName = "returnDate")
    public String getReturnDate() {
        return returnDate;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
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
        CarRecord carRecord = (CarRecord) o;
        return year == carRecord.year && Objects.equals(trackingId, carRecord.trackingId) && Objects.equals(make, carRecord.make) && Objects.equals(model, carRecord.model) && Objects.equals(isAvailable, carRecord.isAvailable) && Objects.equals(dateRented, carRecord.dateRented) && Objects.equals(returnDate, carRecord.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingId, make, model, year, isAvailable, dateRented, returnDate);
    }
}
