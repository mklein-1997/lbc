package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CarCreateRequest;
import com.kenzie.appserver.controller.model.CarResponse;
import com.kenzie.appserver.service.CarService;

import com.kenzie.appserver.service.model.Car;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/Cars")
public class CarController {

    private CarService carService;

    CarController(CarService carService) {
        this.carService = carService;
    }

//    @GetMapping("/{trackingId}")
//    public ResponseEntity<CarResponse> getCarStatus(@PathVariable("trackingId") String trackingId) {
//        Car car = carService.getCarStatus(trackingId);
//
//        if(car == null){
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok().body(carToResponse(car));
//    }


    @PostMapping
    public ResponseEntity<CarResponse> addCar(@RequestBody CarCreateRequest carCreateRequest) {
        Car car = new Car(carCreateRequest.getMake(),carCreateRequest.getModel(),carCreateRequest.getYear());

        try{
            carService.addCar(car);
        }catch(IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body(carToResponse(car));
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
