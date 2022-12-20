package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CarCreateRequest;
import com.kenzie.appserver.controller.model.CarResponse;
import com.kenzie.appserver.service.CarService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/example")
public class CarController {

    private CarService carService;

    CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> get(@PathVariable("id") String id) {



        return ResponseEntity.ok().build();
    }

    //Commented out so the code would compile

    //@PostMapping
    //public ResponseEntity<CarResponse> addNewConcert(@RequestBody CarCreateRequest exampleCreateRequest) {


    //    CarResponse exampleResponse = new CarResponse();

    //    return ResponseEntity.created(URI.create("/example/" + exampleResponse.getId())).body(exampleResponse);
    //}
}
