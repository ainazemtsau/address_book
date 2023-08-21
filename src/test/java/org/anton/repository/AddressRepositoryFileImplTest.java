package org.anton.repository;


import org.anton.entity.Gender;
import org.anton.entity.Person;
import org.anton.loader.PersonDataLoaderFromFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddressRepositoryFileImplTest {

    private PersonDataLoaderFromFile personDataLoaderFromFileMock = mock(PersonDataLoaderFromFile.class);
    private AddressRepositoryFileImpl addressRepositoryTest;

    @BeforeEach
    public void init() {
        when(personDataLoaderFromFileMock.loadData()).thenReturn(Arrays.asList(
                new Person("Person 1", Gender.MALE, LocalDate.now()),
                new Person("Person 2", Gender.FEMALE, LocalDate.now()))
        );

        addressRepositoryTest = new AddressRepositoryFileImpl(personDataLoaderFromFileMock);
    }

    @Test
    public void findByIdMustReturnCorrectPerson() {
        Person person = addressRepositoryTest.findById("Person 1");
        assertNotNull(person);
        assertEquals("Person 1", person.name());
        assertEquals(Gender.MALE, person.gender());
    }
}