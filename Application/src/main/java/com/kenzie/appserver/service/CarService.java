package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.CarResponse;
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
        CarRecord carRecord = new CarRecord();
        carRecord.setId(car.getId());
        carRecord.setMake(car.getMake());
        carRecord.setModel(car.getModel());
        carRecord.setYear(car.getYear());
        carRecord.setAvailable(car.getIsAvailable());
        carRecord.setDateRented(car.getDateRented());
        carRecord.setReturnDate(car.getReturnDate());
        carRepository.save(carRecord);
        return car;
    }

    public Car removeCar(String trackingId) throws CarNotFoundException {

        if (trackingId == null || trackingId.isEmpty()) {
            throw new CarNotFoundException("Tracking ID must not be empty!");
        }

        Car carFromBackend = findById(trackingId);

        if (carFromBackend == null) {
            throw new CarNotFoundException("Tracking ID does not exist");
        }

        carRepository.deleteById(trackingId);

        return carFromBackend;
    }

    public Car findById(String id) {
        return carRepository
                .findById(id)
                .map(car -> new Car(car.getId(), car.getMake(), car.getModel(), car.getYear(), car.getAvailable(),
                        car.getDateRented(), car.getReturnDate()))
                .orElse(null);
    }

    public List<Car> getAllCarsStatus() {
        List<Car> carList = new ArrayList<>();

        Iterable<CarRecord> carIterator = carRepository.findAll();

        carIterator.forEach(carRecord -> carList.add(recordToCar(carRecord)));

        return carList;
    }

    public List<Car> getAllAvailableCars() {
        List<Car> carList = new ArrayList<>();

        Iterable<CarRecord> carIterator = carRepository.findAll();

        carIterator.forEach(carRecord -> {
            if (carRecord.getAvailable()) {
                carList.add(recordToCar(carRecord));
            }
        });

        return carList;
    }

    public List<Car> getAllCarsInService() {
        List<Car> carList = new ArrayList<>();

        Iterable<CarRecord> carIterator = carRepository.findAll();

        carIterator.forEach(carRecord -> {
            if (!carRecord.getAvailable()) {
                if (carRecord.getDateRented().equalsIgnoreCase("n/a") && carRecord.getReturnDate().equalsIgnoreCase("n/a")) {
                    carList.add(recordToCar(carRecord));
                }
            }
        });

        return carList;
    }

    public Car rentCar(String id, String dateRented, String returnDate) {
        Car carFromBackend = findById(id);

        if (carFromBackend == null) {
            throw new CarNotFoundException("Id provided does not match current inventory!");
        }

        carFromBackend.setAvailable(false);
        carFromBackend.setDateRented(dateRented);
        carFromBackend.setReturnDate(returnDate);

        return carFromBackend;
    }

    public Car serviceCar(String id) {
        Car carFromBackend = findById(id);

        if (carFromBackend == null) {
            throw new CarNotFoundException("Id provided does not match current inventory!");
        }

        carFromBackend.setAvailable(false);

        return carFromBackend;
    }

    public Car returnCar(String id) {
        Car carFromBackend = findById(id);

        if (carFromBackend == null) {
            throw new CarNotFoundException("Id provided does not match current inventory!");
        }

        carFromBackend.setAvailable(true);
        carFromBackend.setDateRented("N/A");
        carFromBackend.setReturnDate("N/A");

        return carFromBackend;
    }

    private Car recordToCar(CarRecord carRecord) {
        return new Car(carRecord.getId(), carRecord.getMake(), carRecord.getModel(), carRecord.getYear(),
                carRecord.getAvailable(), carRecord.getDateRented(), carRecord.getReturnDate());
    }
}
