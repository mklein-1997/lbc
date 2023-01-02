package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.model.CarPrimaryKey;
import com.kenzie.appserver.repositories.model.CarRecord;
import com.kenzie.appserver.repositories.CarRepository;
import com.kenzie.appserver.service.model.Car;

import com.kenzie.appserver.service.model.CarNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Car carFromBackend = carRepository
                .findById(new CarPrimaryKey(trackingId))
                .map(carRecord -> new Car(carRecord.getMake(),
                        carRecord.getModel(),
                        carRecord.getYear(),
                        carRecord.getTrackingId(),
                        carRecord.getIsAvailable(),
                        carRecord.getDateRented(),
                        carRecord.getReturnDate()))
                .orElse(null);

        carRepository.deleteById(new CarPrimaryKey(trackingId));
        return carFromBackend;
    }

    public Car getCarStatus(String trackingId) {
        if (trackingId == null || trackingId.isEmpty()) {
            throw new CarNotFoundException("trackingId must not be null or empty!");
        }

        Car carFromBackend = carRepository
                .findById(new CarPrimaryKey(trackingId))
                .map(carRecord -> new Car(carRecord.getMake(),
                        carRecord.getModel(),
                        carRecord.getYear(),
                        carRecord.getTrackingId(),
                        carRecord.getIsAvailable(),
                        carRecord.getDateRented(),
                        carRecord.getReturnDate()))
                .orElse(null);

        if (carFromBackend == null) {
            throw new CarNotFoundException("Car with associated trackingId not found!");
        }

        return carFromBackend;
    }

    public List<Car> getAllCarsStatus() {
        List<Car> carList = new ArrayList<>();

        Iterable<CarRecord> carIterator = carRepository.findAll();
        carIterator.forEach(carRecord -> {
            Car car = new Car(carRecord.getMake(), carRecord.getModel(), carRecord.getYear(),
                    carRecord.getTrackingId(), carRecord.getIsAvailable(), carRecord.getDateRented(),
                    carRecord.getReturnDate());
            carList.add(car);
        });

        return carList;
    }

    public List<Car> getAllAvailableCars() {
        List<Car> carList = new ArrayList<>();

        Iterable<CarRecord> carIterator = carRepository.findAll();
        carIterator.forEach(carRecord -> {
            if (carRecord.getIsAvailable()) {
                Car car = new Car(carRecord.getMake(), carRecord.getModel(), carRecord.getYear(),
                        carRecord.getTrackingId(), carRecord.getIsAvailable(), carRecord.getDateRented(),
                        carRecord.getReturnDate());
                carList.add(car);
            }
        });

        return carList;
    }

    public List<Car> getAllCarsInService() {
        List<Car> carList = new ArrayList<>();

        Iterable<CarRecord> carIterator = carRepository.findAll();
        carIterator.forEach(carRecord -> {
            if (!carRecord.getIsAvailable()) {
                if (carRecord.getDateRented() == null && carRecord.getReturnDate() == null) {
                    Car car = new Car(carRecord.getMake(), carRecord.getModel(), carRecord.getYear(),
                            carRecord.getTrackingId(), carRecord.getIsAvailable(), carRecord.getDateRented(),
                            carRecord.getReturnDate());
                    carList.add(car);
                }
            }
        });

        return carList;
    }
    
}
