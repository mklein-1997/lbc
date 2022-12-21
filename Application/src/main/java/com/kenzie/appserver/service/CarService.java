package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.model.CarRecord;
import com.kenzie.appserver.repositories.CarRepository;
import com.kenzie.appserver.service.model.Car;

import org.springframework.stereotype.Service;

@Service
public class CarService {
    private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car addCar(Car car) {
        CarRecord record = new CarRecord();
        record.setMake(car.getMake());
        record.setModel(car.getModel());
        record.setYear(car.getYear());
        record.setTrackingId(car.getTrackingId());
        record.setAvailable(car.getAvailable());
        record.setDateRented(car.getDateRented());
        record.setReturnDate(car.getReturnDate());
        carRepository.save(record);
        return car;
    }

    public Car removeCar(String trackingId) {
        Car car = carRepository.findByTrackingId(trackingId);
        carRepository.deleteById(trackingId);
        return car;
    }
}
