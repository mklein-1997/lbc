package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CarRepository;
import com.kenzie.appserver.repositories.model.CarRecord;
import com.kenzie.appserver.service.model.Car;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

public class CarServiceTest {
    private CarRepository carRepository;
    private CarService carService;

    @BeforeEach
    void setup() {
        carRepository = mock(CarRepository.class);
        carService = new CarService(carRepository);
    }
    /** ------------------------------------------------------------------------
     *  carService.addCar()
     *  ------------------------------------------------------------------------ **/

    @Test
    public void addCar() {
        //GIVEN
        Car car = new Car("Chevrolet", "Camaro", 1977);

        ArgumentCaptor<CarRecord> carRecordCaptor = ArgumentCaptor.forClass(CarRecord.class);

        //WHEN
        Car returnedCar = carService.addCar(car);

        //THEN
        Assertions.assertNotNull(returnedCar);

        verify(carRepository).save(carRecordCaptor.capture());

        CarRecord record = carRecordCaptor.getValue();

        Assertions.assertNotNull(record, "The record is returned");
        Assertions.assertEquals(record.getTrackingId(), car.getTrackingId(), "The tracking id matches");
        Assertions.assertEquals(record.getMake(), car.getMake(), "The make matches");
        Assertions.assertEquals(record.getModel(), car.getModel(), "The model matches");
        Assertions.assertEquals(record.getYear(), car.getYear(), "The year matches");
        Assertions.assertEquals(record.getIsAvailable(), car.getAvailable(), "The availability matches");
        Assertions.assertEquals(record.getDateRented(), car.getDateRented(), "The rental date matches");
        Assertions.assertEquals(record.getReturnDate(), car.getReturnDate(), "The return date matches");
    }

    /** ------------------------------------------------------------------------
     *  carService.removeCar()
     *  ------------------------------------------------------------------------ **/

    @Test
    public void removeCar() {
        //GIVEN
        String trackingId = randomUUID().toString();

        //WHEN
        carService.removeCar(trackingId);

        //THEN
        verify(carRepository).deleteById(trackingId);

    }

}
