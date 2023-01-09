package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CarCreateRequest;
import com.kenzie.appserver.controller.model.CarResponse;
import com.kenzie.appserver.service.CarService;

import com.kenzie.appserver.service.model.Car;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/cars")
public class CarController {

    private CarService carService;

    CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getCarStatus(@PathVariable("id") String id) {

        Car car = carService.findById(id);
        if (car == null) {
            return ResponseEntity.notFound().build();
        }

        CarResponse carResponse = new CarResponse();
        carResponse.setId(car.getId());
        carResponse.setMake(car.getMake());
        carResponse.setModel(car.getModel());
        carResponse.setYear(car.getYear());
        carResponse.setIsAvailable(car.getIsAvailable());
        carResponse.setDateRented(car.getDateRented());
        carResponse.setReturnDate(car.getReturnDate());
        return ResponseEntity.ok(carResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarResponse>> getAllCarsStatus() {
        List<Car> cars = carService.getAllCarsStatus();

        if (cars == null || cars.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CarResponse> carResponses = new ArrayList<>();
        for (Car car : cars) {
            carResponses.add(this.carToResponse(car));
        }

        return ResponseEntity.ok(carResponses);
    }

    @PostMapping
    public ResponseEntity<CarResponse> addCar(@RequestBody CarCreateRequest carCreateRequest) {
        Car car = new Car(randomUUID().toString(),
                carCreateRequest.getMake(), carCreateRequest.getModel(), carCreateRequest.getYear(), true,
                "n/a", "n/a");
        carService.addCar(car);

        CarResponse carResponse = new CarResponse();
        carResponse.setId(car.getId());
        carResponse.setMake(car.getMake());
        carResponse.setModel(car.getModel());
        carResponse.setYear(car.getYear());
        carResponse.setIsAvailable(car.getIsAvailable());
        carResponse.setDateRented(car.getDateRented());
        carResponse.setReturnDate(car.getReturnDate());

        return ResponseEntity.created(URI.create("/cars/" + carResponse.getId())).body(carResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CarResponse> removeCar(@PathVariable("id") String trackingId){
        Car car = carService.removeCar(trackingId);

        if(car == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok().body(carToResponse(car));
    }

    @GetMapping("/available")
    public ResponseEntity<List<CarResponse>> getAvailableCars() {
        List<Car> cars = carService.getAllAvailableCars();

        if (cars == null || cars.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CarResponse> carResponses = new ArrayList<>();
        for (Car car : cars) {
            carResponses.add(this.carToResponse(car));
        }

        return ResponseEntity.ok(carResponses);
    }

    @GetMapping("/service")
    public ResponseEntity<List<CarResponse>> getAllCarsInService() {
        List<Car> cars = carService.getAllCarsInService();

        if (cars == null || cars.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CarResponse> carResponses = new ArrayList<>();
        for (Car car : cars) {
            carResponses.add(this.carToResponse(car));
        }

        return ResponseEntity.ok(carResponses);
    }

//    @PutMapping(
//    public ResponseEntity<CarResponse> updateAvailabilityStatus{}

    private CarResponse carToResponse(Car car){
        CarResponse carResponse = new CarResponse();
        carResponse.setId(car.getId());
        carResponse.setMake(car.getMake());
        carResponse.setModel(car.getModel());
        carResponse.setYear(car.getYear());
        carResponse.setIsAvailable(car.getIsAvailable());
        carResponse.setDateRented(car.getDateRented());
        carResponse.setReturnDate(car.getReturnDate());

        return carResponse;
    }
}
