package org.anton.repository;


import org.anton.entity.Gender;
import org.anton.entity.Person;
import org.anton.loader.PersonDataLoaderFromFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonRepositoryFileImplTest {

    private final PersonDataLoaderFromFile personDataLoaderFromFileMock = mock(PersonDataLoaderFromFile.class);
    private PersonRepositoryFileImpl repository;

    @BeforeEach
    void setUp() {
        when(personDataLoaderFromFileMock.loadData()).thenReturn(List.of(
                new Person("Bill McKnight", Gender.MALE, LocalDate.of(1980, 1, 1)),
                new Person("James Brown", Gender.MALE, LocalDate.of(1985, 5, 10))
        ));
        repository = new PersonRepositoryFileImpl(personDataLoaderFromFileMock);
    }

    @Test
    void testFindByFullName() {
        Optional<Person> foundPerson = repository.findByName("Bill McKnight");
        assertTrue(foundPerson.isPresent());
        assertEquals("Bill McKnight", foundPerson.get().name());
    }

    @Test
    void testFindByPartialName() {
        Optional<Person> foundPerson = repository.findByName("Bill");
        assertTrue(foundPerson.isPresent());
        assertEquals("Bill McKnight", foundPerson.get().name());
    }

    @Test
    void testFindByNameNotFound() {
        Optional<Person> foundPerson = repository.findByName("NonExistentName");
        assertFalse(foundPerson.isPresent());
    }
}