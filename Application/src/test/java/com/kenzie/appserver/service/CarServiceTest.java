package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CarRepository;
import com.kenzie.appserver.repositories.model.CarRecord;
import com.kenzie.appserver.service.model.Car;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CarServiceTest {
    private CarRepository exampleRepository;
    private CarService exampleService;

    @BeforeEach
    void setup() {
        exampleRepository = mock(CarRepository.class);
        exampleService = new CarService(exampleRepository);
    }
    /** ------------------------------------------------------------------------
     *  exampleService.findById
     *  ------------------------------------------------------------------------ **/

    //@Test
    //void findById() {
    //    // GIVEN
    //    String id = randomUUID().toString();

    //    CarRecord record = new CarRecord();
    //    record.setId(id);
    //    record.setName("concertname");

    //    // WHEN
    //    when(exampleRepository.findById(id)).thenReturn(Optional.of(record));
    //    Car example = exampleService.findById(id);

        // THEN
    //    Assertions.assertNotNull(example, "The object is returned");
    //    Assertions.assertEquals(record.getId(), example.getId(), "The id matches");
    //    Assertions.assertEquals(record.getName(), example.getName(), "The name matches");
    //}

    //@Test
    //void findByConcertId_invalid() {
    //    // GIVEN
    //    String id = randomUUID().toString();

    //    when(exampleRepository.findById(id)).thenReturn(Optional.empty());

    //    // WHEN
    //    Car example = exampleService.findById(id);

    //    // THEN
    //    Assertions.assertNull(example, "The example is null when not found");
    //}

}
