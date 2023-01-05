package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CarRepository;
import com.kenzie.appserver.repositories.model.CarRecord;
import com.kenzie.appserver.service.model.Car;
import com.kenzie.appserver.service.model.CarNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
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

    /** ------------------------------------------------------------------------
     *  carService.getCarStatus()
     *  ------------------------------------------------------------------------ **/

    @Test
    public void getCarStatus() {
        //GIVEN
        Car testCar = new Car("Chevrolet", "Camaro", 1977,
                randomUUID().toString(), true, "N/A", "N/A");

        CarRecord record = new CarRecord();
        record.setMake(testCar.getMake());
        record.setModel(testCar.getModel());
        record.setYear(testCar.getYear());
        record.setTrackingId(testCar.getTrackingId());
        record.setAvailable(testCar.getAvailable());
        record.setDateRented(testCar.getDateRented());
        record.setReturnDate(testCar.getReturnDate());

        when(carRepository.findById(any())).thenReturn(Optional.of(record));

        //WHEN
        Car result = carService.getCarStatus(testCar.getTrackingId());

        //THEN
        Assertions.assertEquals(testCar, result, "getCarStatus returns the correct data");
    }

    @Test
    public void getCarStatus_invalidData_throwsCarNotFoundException() {
        //GIVEN
        String trackingId = "";

        //WHEN && THEN
        Assertions.assertThrows(CarNotFoundException.class, () ->
                carService.getCarStatus(trackingId));
    }

    @Test
    public void getCarStatus_repoReturnsNull_throwsCarNotFoundException() {
        //GIVEN
        String trackingId = randomUUID().toString();

        when(carRepository.findById(any())).thenReturn(Optional.empty());

        //WHEN && THEN
        Assertions.assertThrows(CarNotFoundException.class, () ->
                carService.getCarStatus(trackingId));
    }

    /** ------------------------------------------------------------------------
     *  carService.getAllCarsStatus()
     *  ------------------------------------------------------------------------ **/

    @Test
    public void getAllCarsStatus_emptyIterator_throwsCarNotFoundException() {
        //GIVEN
        when(carRepository.findAll()).thenReturn(new ArrayList<>());

        //WHEN && THEN
        Assertions.assertThrows(CarNotFoundException.class, () -> carService.getAllCarsStatus());
    }

}
