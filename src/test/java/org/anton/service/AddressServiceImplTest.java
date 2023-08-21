package org.anton.service;

import org.anton.entity.Gender;
import org.anton.entity.Person;
import org.anton.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddressServiceImplTest {
    private final AddressRepository addressRepositoryTest = mock(AddressRepository.class);
    private final AddressService addressServiceTest = new AddressServiceImpl(addressRepositoryTest);

    @BeforeEach
    public void init() {
        when(addressRepositoryTest.getAllPerson()).thenReturn(List.of(
                new Person("Person 1", Gender.MALE, LocalDate.now()),
                new Person("Person 2", Gender.MALE, LocalDate.now()),
                new Person("Person 3", Gender.MALE, LocalDate.now()),
                new Person("Person 4", Gender.FEMALE, LocalDate.now()),
                new Person("Person 5", Gender.FEMALE, LocalDate.now()))
        );
    }

    @Test
    public void malesCountMustReturn3() {
        var malesCount = addressServiceTest.countMales();
        assertEquals(3, malesCount);
    }

    @Test
    public void malesCountMustReturnZeroWhenRepositoryReturnsNull() {
        when(addressRepositoryTest.getAllPerson()).thenReturn(Collections.emptyList() );

        var malesCount = addressServiceTest.countMales();
        assertEquals(0, malesCount);
    }

    @Test
    public void malesCountMustReturnZeroWhenRepositoryReturnsEmptyList() {
        when(addressRepositoryTest.getAllPerson()).thenReturn(List.of());

        var malesCount = addressServiceTest.countMales();
        assertEquals(0, malesCount);
    }

    @Test
    public void malesCountMustReturnZeroWhenAllPersonsAreFemale() {
        when(addressRepositoryTest.getAllPerson()).thenReturn(List.of(
                new Person("Person 1", Gender.FEMALE, LocalDate.now()),
                new Person("Person 2", Gender.FEMALE, LocalDate.now()))
        );

        var malesCount = addressServiceTest.countMales();
        assertEquals(0, malesCount);
    }
}