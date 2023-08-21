package org.anton.repository;


import org.anton.entity.Gender;
import org.anton.entity.Person;
import org.anton.loader.PersonDataLoaderFromFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonRepositoryFileImplTest {

    private final PersonDataLoaderFromFile personDataLoaderFromFileMock = mock(PersonDataLoaderFromFile.class);
    private PersonRepositoryFileImpl addressRepositoryTest;

    @BeforeEach
    public void init() {
        when(personDataLoaderFromFileMock.loadData()).thenReturn(Arrays.asList(
                new Person("Person 1", Gender.MALE, LocalDate.now()),
                new Person("Person 2", Gender.FEMALE, LocalDate.now()))
        );

        addressRepositoryTest = new PersonRepositoryFileImpl(personDataLoaderFromFileMock);
    }

    @Test
    public void findByIdMustReturnCorrectPerson() {
        Person person = addressRepositoryTest.findById("Person 1");
        assertNotNull(person);
        assertEquals("Person 1", person.name());
        assertEquals(Gender.MALE, person.gender());
    }
}