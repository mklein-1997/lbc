package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.data.annotation.Id;

import java.util.Objects;

@DynamoDBTable(tableName = "Car")
public class CarRecord {

    private String id;
    private String make;
    private String model;
    private int year;
    private boolean isAvailable;
    private String dateRented;
    private String returnDate;

    @DynamoDBHashKey(attributeName = "Id")
    public String getId() {
        return id;
    }

    @DynamoDBAttribute(attributeName = "Make")
    public String getMake() {
        return make;
    }

    @DynamoDBAttribute(attributeName = "Model")
    public String getModel() {
        return model;
    }

    @DynamoDBAttribute(attributeName = "Year")
    public int getYear() {
        return year;
    }

    @DynamoDBAttribute(attributeName = "IsAvailable")
    public boolean getAvailable() {
        return isAvailable;
    }

    @DynamoDBAttribute(attributeName = "DateRented")
    public String getDateRented() {
        return dateRented;
    }

    @DynamoDBAttribute(attributeName = "ReturnDate")
    public String getReturnDate() {
        return returnDate;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setAvailable(boolean available) {
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
        return year == carRecord.year && isAvailable == carRecord.isAvailable && id.equals(carRecord.id) && make.equals(carRecord.make) && model.equals(carRecord.model) && dateRented.equals(carRecord.dateRented) && returnDate.equals(carRecord.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, make, model, year, isAvailable, dateRented, returnDate);
    }
}
