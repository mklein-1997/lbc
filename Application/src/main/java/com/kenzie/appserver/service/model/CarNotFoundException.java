package com.kenzie.appserver.service.model;

public class CarNotFoundException extends RuntimeException{

    public CarNotFoundException (String message){
        super(message);
    }

    public CarNotFoundException (String message, Throwable t) {
        super(message, t);
    }
}
