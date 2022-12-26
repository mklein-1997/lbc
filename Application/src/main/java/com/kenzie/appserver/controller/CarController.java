package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CarCreateRequest;
import com.kenzie.appserver.controller.model.CarResponse;
import com.kenzie.appserver.service.CarService;

import com.kenzie.appserver.service.model.Car;
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
//
//
//        return ResponseEntity.ok().build();
//    }

    //Commented out so the code would compile

    @PostMapping
    public ResponseEntity<CarResponse> addCar(@RequestBody CarCreateRequest carCreateRequest) {


        CarResponse carResponse = new CarResponse();

        return ResponseEntity.created(URI.create("/example/" + exampleResponse.getId())).body(exampleResponse);
    }
}
