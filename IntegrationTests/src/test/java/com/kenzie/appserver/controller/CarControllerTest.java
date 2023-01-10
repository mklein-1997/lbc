package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.CarCreateRequest;
import com.kenzie.appserver.controller.model.CarUpdateRequest;
import com.kenzie.appserver.service.CarService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.service.model.Car;
import net.andreinc.mockneat.MockNeat;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
class CarControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    CarService carService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    private Car testCar;

    @BeforeEach
    void setup(){
        String id = UUID.randomUUID().toString();
        String model = mockNeat.strings().valStr();
        String make = mockNeat.strings().valStr();
        int year = 5000;
        boolean isAvailable = true;
        String dateRented = "N/A";
        String returnDate = "N/A";
        testCar = new Car(id,make,model,year,isAvailable,dateRented,returnDate);
    }

    //Commented out so the code would compile

    @Test
    public void getCarStatus_exists_returns200Status() throws Exception {

        Car persistedExample = carService.addCar(testCar);
        mvc.perform(get("/cars/{id}", persistedExample.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id")
                        .value(is(testCar.getId())))
                .andExpect(jsonPath("make")
                        .value(is(testCar.getMake())))
                .andExpect(jsonPath("model")
                        .value(is(testCar.getModel())))
                .andExpect(jsonPath("year")
                        .value(is(testCar.getYear())))
                .andExpect(status().isOk());
    }

    @Test
    public void getCarStatus_doesntExists_returns4xxStatus() throws Exception {

        mvc.perform(get("/cars/{id}", testCar.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addCar_withValidCar_returns2xxStatus() throws Exception {

        CarCreateRequest carCreateRequest = new CarCreateRequest();
        carCreateRequest.setMake(testCar.getMake());
        carCreateRequest.setModel(testCar.getModel());
        carCreateRequest.setYear(testCar.getYear());


        mvc.perform(post("/cars")
                        .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(carCreateRequest)))
                .andExpect(jsonPath("make")
                        .value(is(testCar.getMake())))
                .andExpect(jsonPath("model")
                        .value(is(testCar.getModel())))
                .andExpect(jsonPath("year")
                        .value(is(testCar.getYear())))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void deleteCar_withValidCar_returns200Status() throws Exception {

        Car persistedExample = carService.addCar(testCar);

        mvc.perform(delete("/cars/{id}",testCar.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateAvailabilityStatus_validData_returns2xxStatus() throws Exception {

        carService.addCar(testCar);
        CarUpdateRequest carUpdateRequest = new CarUpdateRequest();
        carUpdateRequest.setMake(testCar.getMake());
        carUpdateRequest.setModel(testCar.getModel());
        carUpdateRequest.setAvailable(false);
        carUpdateRequest.setDateRented("12/3");
        carUpdateRequest.setReturnDate("12/6");
        carUpdateRequest.setYear(testCar.getYear());
        carUpdateRequest.setTrackingId(testCar.getId());

        mvc.perform(put("/cars/{id}",testCar.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carUpdateRequest)))
                .andExpect(jsonPath("id")
                        .value(is(carUpdateRequest.getId())))
                .andExpect(jsonPath("make")
                        .value(is(carUpdateRequest.getMake())))
                .andExpect(jsonPath("model")
                        .value(is(carUpdateRequest.getModel())))
                .andExpect(jsonPath("year")
                        .value(is(carUpdateRequest.getYear())))
                .andExpect(jsonPath("isAvailable")
                        .value(is(carUpdateRequest.isAvailable())))
                .andExpect(jsonPath("dateRented")
                        .value(is(carUpdateRequest.getDateRented())))
                .andExpect(jsonPath("returnDate")
                        .value(is(carUpdateRequest.getReturnDate())))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void updateAvailabilityStatus_carNotFound_returns4xxStatus() throws Exception {

        CarUpdateRequest carUpdateRequest = new CarUpdateRequest();
        carUpdateRequest.setTrackingId(UUID.randomUUID().toString());

        mvc.perform(put("/cars/{id}",carUpdateRequest.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carUpdateRequest)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateAvailabilityStatus_carAlreadyRented_returns4xxStatus() throws Exception {

        testCar.setAvailable(false);
        testCar.setDateRented("12/1");
        testCar.setReturnDate("12/8");
        carService.addCar(testCar);

        CarUpdateRequest carUpdateRequest = new CarUpdateRequest();
        carUpdateRequest.setMake(testCar.getMake());
        carUpdateRequest.setModel(testCar.getModel());
        carUpdateRequest.setAvailable(false);
        carUpdateRequest.setDateRented("12/3");
        carUpdateRequest.setReturnDate("12/6");
        carUpdateRequest.setYear(testCar.getYear());
        carUpdateRequest.setTrackingId(testCar.getId());


        mvc.perform(put("/cars/{id}",testCar.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carUpdateRequest)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getAllCarsStatus_exists_returns200Status() throws Exception {

        Car car = carService.addCar(testCar);
        mvc.perform(get("/cars/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    public void getAvailableCars_exists_returns200Status() throws Exception {

        Car car = carService.addCar(testCar);
        mvc.perform(get("/cars/available")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getAllCarsInService_exists_returns200Status() throws Exception {

        Car car = carService.addCar(testCar);
        mvc.perform(get("/cars/service")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

}