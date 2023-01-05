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

@RestController
@RequestMapping("/cars")
public class CarController {

    private CarService carService;

    CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<CarResponse> getCarStatus(@PathVariable("trackingId") String trackingId) {
        Car car = carService.findById(trackingId);

        if(car == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(carToResponse(car));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarResponse>> getAllCarsStatus() {
        List<Car> cars = carService.getAllCarsStatus();

        if (cars == null || cars.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<CarResponse> carResponses = new ArrayList<>();
        for (Car car : cars) {
            carResponses.add(carToResponse(car));
        }

        return ResponseEntity.ok(carResponses);
    }


    @PostMapping
    public ResponseEntity<CarResponse> addCar(@RequestBody CarCreateRequest carCreateRequest) {
        Car car = new Car(carCreateRequest.getMake(),carCreateRequest.getModel(),carCreateRequest.getYear());

        try{
            carService.addCar(car);
        }catch(IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.created(URI.create("/cars/" + carToResponse(car).getTrackingId())).body(carToResponse(car));
    }

    @DeleteMapping("/{trackingId}")
    public ResponseEntity<CarResponse> removeCar(@PathVariable("trackingId") String trackingId){
        Car car = carService.removeCar(trackingId);

        if(car == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok().body(carToResponse(car));
    }

    private CarResponse carToResponse(Car car){
        CarResponse carResponse = new CarResponse();
        carResponse.setTrackingId(car.getTrackingId());
        carResponse.setMake(car.getMake());
        carResponse.setModel(car.getModel());
        carResponse.setYear(car.getYear());
        carResponse.setAvailable(car.getAvailable());
        carResponse.setDateRented(car.getDateRented());
        carResponse.setReturnDate(car.getReturnDate());

        return carResponse;
    }
}
