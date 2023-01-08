
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
import java.util.List;
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
     * carService.addCar()
     * ------------------------------------------------------------------------**/


    @Test
    public void addCar() {
        //GIVEN
        Car car = new Car(randomUUID().toString(), "Chevrolet", "Camaro",
                1977, true, "n/a", "n/a");

        ArgumentCaptor<CarRecord> carRecordCaptor = ArgumentCaptor.forClass(CarRecord.class);

        //WHEN
        Car returnedCar = carService.addCar(car);

        //THEN
        Assertions.assertNotNull(returnedCar);

        verify(carRepository).save(carRecordCaptor.capture());

        CarRecord record = carRecordCaptor.getValue();

        Assertions.assertNotNull(record, "The record is returned");
        Assertions.assertEquals(record.getId(), car.getId(), "The tracking id matches");
        Assertions.assertEquals(record.getMake(), car.getMake(), "The make matches");
        Assertions.assertEquals(record.getModel(), car.getModel(), "The model matches");
        Assertions.assertEquals(record.getYear(), car.getYear(), "The year matches");
        Assertions.assertEquals(record.getAvailable(), car.getIsAvailable(), "The availability matches");
        Assertions.assertEquals(record.getDateRented(), car.getDateRented(), "The rental date matches");
        Assertions.assertEquals(record.getReturnDate(), car.getReturnDate(), "The return date matches");
    }



    /** ------------------------------------------------------------------------
     * carService.removeCar()
     * ------------------------------------------------------------------------**/


    @Test
    public void removeCar() {
        //GIVEN
        String trackingId = randomUUID().toString();

        Car testCar = new Car(randomUUID().toString(), "Chevrolet", "Camaro", 1977,
                true, "N/A", "N/A");

        CarRecord record = new CarRecord();
        record.setMake(testCar.getMake());
        record.setModel(testCar.getModel());
        record.setYear(testCar.getYear());
        record.setId(testCar.getId());
        record.setAvailable(testCar.getIsAvailable());
        record.setDateRented(testCar.getDateRented());
        record.setReturnDate(testCar.getReturnDate());

        when(carRepository.findById(any())).thenReturn(Optional.of(record));

        //WHEN
        carService.removeCar(trackingId);

        //THEN
        verify(carRepository).deleteById(trackingId);
    }

    /** ------------------------------------------------------------------------
     * carService.findById()
     * ------------------------------------------------------------------------**/


    @Test
    public void findById() {
        //GIVEN
        Car testCar = new Car(randomUUID().toString(), "Chevrolet", "Camaro", 1977,
                true, "N/A", "N/A");

        CarRecord record = new CarRecord();
        record.setMake(testCar.getMake());
        record.setModel(testCar.getModel());
        record.setYear(testCar.getYear());
        record.setId(testCar.getId());
        record.setAvailable(testCar.getIsAvailable());
        record.setDateRented(testCar.getDateRented());
        record.setReturnDate(testCar.getReturnDate());

        when(carRepository.findById(any())).thenReturn(Optional.of(record));

        //WHEN
        Car result = carService.findById(testCar.getId());

        //THEN
        Assertions.assertEquals(testCar, result, "getCarStatus returns the correct data");
    }

    @Test
    public void findById_invalidId_returnsNull() {
        //GIVEN
        String id = randomUUID().toString();
        when(carRepository.findById(id)).thenReturn(Optional.empty());

        //WHEN
        Car result = carService.findById(id);

        //THEN
        Assertions.assertNull(result);
    }

    /** ------------------------------------------------------------------------
    *  carService.getAllCarsStatus()
    *  ------------------------------------------------------------------------ **/

    @Test
    public void getAllCarsStatus() {
        //GIVEN
        Car testCar = new Car(randomUUID().toString(), "Chevrolet", "Camaro", 1977,
                true, "N/A", "N/A");

        CarRecord record = new CarRecord();
        record.setMake(testCar.getMake());
        record.setModel(testCar.getModel());
        record.setYear(testCar.getYear());
        record.setId(testCar.getId());
        record.setAvailable(testCar.getIsAvailable());
        record.setDateRented(testCar.getDateRented());
        record.setReturnDate(testCar.getReturnDate());

        List<CarRecord> recordList = new ArrayList<>();
        recordList.add(record);

        when(carRepository.findAll()).thenReturn(recordList);

        //WHEN
        List<Car> carList = carService.getAllCarsStatus();

        //THEN
        Assertions.assertTrue(carList.contains(testCar));
    }

    /** ------------------------------------------------------------------------
     *  carService.getAllAvailableCars()
     *  ------------------------------------------------------------------------ **/

    @Test
    public void getAllAvailableCars() {
        //GIVEN
        Car testCar = new Car(randomUUID().toString(), "Chevrolet", "Camaro", 1977,
                true, "N/A", "N/A");

        CarRecord record = new CarRecord();
        record.setMake(testCar.getMake());
        record.setModel(testCar.getModel());
        record.setYear(testCar.getYear());
        record.setId(testCar.getId());
        record.setAvailable(testCar.getIsAvailable());
        record.setDateRented(testCar.getDateRented());
        record.setReturnDate(testCar.getReturnDate());

        List<CarRecord> recordList = new ArrayList<>();
        recordList.add(record);

        when(carRepository.findAll()).thenReturn(recordList);

        //WHEN
        List<Car> carList = carService.getAllAvailableCars();

        //THEN
        Assertions.assertTrue(carList.contains(testCar));
    }

    @Test
    public void getAllAvailableCars_carNotAvailable_notReturned() {
        //GIVEN
        Car testCar = new Car(randomUUID().toString(), "Chevrolet", "Camaro", 1977,
                false, "N/A", "N/A");

        CarRecord record = new CarRecord();
        record.setMake(testCar.getMake());
        record.setModel(testCar.getModel());
        record.setYear(testCar.getYear());
        record.setId(testCar.getId());
        record.setAvailable(testCar.getIsAvailable());
        record.setDateRented(testCar.getDateRented());
        record.setReturnDate(testCar.getReturnDate());

        List<CarRecord> recordList = new ArrayList<>();
        recordList.add(record);

        when(carRepository.findAll()).thenReturn(recordList);

        //WHEN
        List<Car> carList = carService.getAllAvailableCars();

        //THEN
        Assertions.assertFalse(carList.contains(testCar));
    }

    /** ------------------------------------------------------------------------
     *  carService.getAllCarsInService()
     *  ------------------------------------------------------------------------ **/

    @Test
    public void getAllCarsInService() {
        //GIVEN
        Car testCar = new Car(randomUUID().toString(), "Chevrolet", "Camaro", 1977,
                false, "N/A", "N/A");

        CarRecord record = new CarRecord();
        record.setMake(testCar.getMake());
        record.setModel(testCar.getModel());
        record.setYear(testCar.getYear());
        record.setId(testCar.getId());
        record.setAvailable(testCar.getIsAvailable());
        record.setDateRented(testCar.getDateRented());
        record.setReturnDate(testCar.getReturnDate());

        List<CarRecord> recordList = new ArrayList<>();
        recordList.add(record);

        when(carRepository.findAll()).thenReturn(recordList);

        //WHEN
        List<Car> carList = carService.getAllCarsInService();

        //THEN
        Assertions.assertTrue(carList.contains(testCar));
    }

    @Test
    public void getAllCarsInService_carIsAvailable_notReturned() {
        //GIVEN
        Car testCar = new Car(randomUUID().toString(), "Chevrolet", "Camaro", 1977,
                true, "N/A", "N/A");

        CarRecord record = new CarRecord();
        record.setMake(testCar.getMake());
        record.setModel(testCar.getModel());
        record.setYear(testCar.getYear());
        record.setId(testCar.getId());
        record.setAvailable(testCar.getIsAvailable());
        record.setDateRented(testCar.getDateRented());
        record.setReturnDate(testCar.getReturnDate());

        List<CarRecord> recordList = new ArrayList<>();
        recordList.add(record);

        when(carRepository.findAll()).thenReturn(recordList);

        //WHEN
        List<Car> carList = carService.getAllCarsInService();

        //THEN
        Assertions.assertFalse(carList.contains(testCar));
    }

    @Test
    public void getAllCarsInService_carIsRented_notReturned() {
        //GIVEN
        Car testCar = new Car(randomUUID().toString(), "Chevrolet", "Camaro", 1977,
                false, "rented", "rented");

        CarRecord record = new CarRecord();
        record.setMake(testCar.getMake());
        record.setModel(testCar.getModel());
        record.setYear(testCar.getYear());
        record.setId(testCar.getId());
        record.setAvailable(testCar.getIsAvailable());
        record.setDateRented(testCar.getDateRented());
        record.setReturnDate(testCar.getReturnDate());

        List<CarRecord> recordList = new ArrayList<>();
        recordList.add(record);

        when(carRepository.findAll()).thenReturn(recordList);

        //WHEN
        List<Car> carList = carService.getAllCarsInService();

        //THEN
        Assertions.assertFalse(carList.contains(testCar));
    }

    /** ------------------------------------------------------------------------
     *  carService.rentCar()
     *  ------------------------------------------------------------------------ **/

    @Test
    public void rentCar() {
        //GIVEN
        Car testCar = new Car(randomUUID().toString(), "Chevrolet", "Camaro", 1977,
                true, "N/A", "N/A");

        CarRecord record = new CarRecord();
        record.setMake(testCar.getMake());
        record.setModel(testCar.getModel());
        record.setYear(testCar.getYear());
        record.setId(testCar.getId());
        record.setAvailable(testCar.getIsAvailable());
        record.setDateRented(testCar.getDateRented());
        record.setReturnDate(testCar.getReturnDate());

        when(carRepository.findById(any())).thenReturn(Optional.of(record));

        ArgumentCaptor<CarRecord> carRecordCaptor = ArgumentCaptor.forClass(CarRecord.class);

        //WHEN
        carService.rentCar(testCar.getId(), "rented", "returned");

        //THEN
        verify(carRepository).save(carRecordCaptor.capture());

        CarRecord result = carRecordCaptor.getValue();

        Assertions.assertNotEquals("N/A", result.getDateRented());
        Assertions.assertNotEquals("N/A", result.getReturnDate());
        Assertions.assertFalse(result.getAvailable());
    }

    @Test
    public void rentCar_repoReturnsNull_throwsCarNotFoundException() {
        //GIVEN
        String id = randomUUID().toString();
        when(carRepository.findById(id)).thenReturn(Optional.empty());

        //WHEN && THEN
        Assertions.assertThrows(CarNotFoundException.class, () ->
                carService.rentCar(id, "rented", "returned"));
    }

    /** ------------------------------------------------------------------------
     *  carService.serviceCar()
     *  ------------------------------------------------------------------------ **/

    @Test
    public void serviceCar() {
        //GIVEN
        Car testCar = new Car(randomUUID().toString(), "Chevrolet", "Camaro", 1977,
                true, "N/A", "N/A");

        CarRecord record = new CarRecord();
        record.setMake(testCar.getMake());
        record.setModel(testCar.getModel());
        record.setYear(testCar.getYear());
        record.setId(testCar.getId());
        record.setAvailable(testCar.getIsAvailable());
        record.setDateRented(testCar.getDateRented());
        record.setReturnDate(testCar.getReturnDate());

        when(carRepository.findById(any())).thenReturn(Optional.of(record));

        ArgumentCaptor<CarRecord> carRecordCaptor = ArgumentCaptor.forClass(CarRecord.class);

        //WHEN
        carService.serviceCar(testCar.getId());

        //THEN
        verify(carRepository).save(carRecordCaptor.capture());

        CarRecord result = carRecordCaptor.getValue();

        Assertions.assertEquals("N/A", result.getDateRented());
        Assertions.assertEquals("N/A", result.getReturnDate());
        Assertions.assertFalse(result.getAvailable());
    }

    @Test
    public void serviceCar_repoReturnsNull_throwsCarNotFoundException() {
        //GIVEN
        String id = randomUUID().toString();
        when(carRepository.findById(id)).thenReturn(Optional.empty());

        //WHEN && THEN
        Assertions.assertThrows(CarNotFoundException.class, () ->
                carService.serviceCar(id));
    }

    /** ------------------------------------------------------------------------
     *  carService.returnCar()
     *  ------------------------------------------------------------------------ **/

    @Test
    public void returnCar() {
        //GIVEN
        Car testCar = new Car(randomUUID().toString(), "Chevrolet", "Camaro", 1977,
                true, "N/A", "N/A");

        CarRecord record = new CarRecord();
        record.setMake(testCar.getMake());
        record.setModel(testCar.getModel());
        record.setYear(testCar.getYear());
        record.setId(testCar.getId());
        record.setAvailable(testCar.getIsAvailable());
        record.setDateRented(testCar.getDateRented());
        record.setReturnDate(testCar.getReturnDate());

        when(carRepository.findById(any())).thenReturn(Optional.of(record));

        ArgumentCaptor<CarRecord> carRecordCaptor = ArgumentCaptor.forClass(CarRecord.class);

        //WHEN
        carService.returnCar(testCar.getId());

        //THEN
        verify(carRepository).save(carRecordCaptor.capture());

        CarRecord result = carRecordCaptor.getValue();

        Assertions.assertTrue(result.getAvailable());
        Assertions.assertEquals("N/A", result.getDateRented());
        Assertions.assertEquals("N/A", result.getReturnDate());
    }

    @Test
    public void returnCar_repoReturnsNull_throwsCarNotFoundException() {
        //GIVEN
        String id = randomUUID().toString();
        when(carRepository.findById(id)).thenReturn(Optional.empty());

        //WHEN && THEN
        Assertions.assertThrows(CarNotFoundException.class, () ->
                carService.returnCar(id));
    }
}