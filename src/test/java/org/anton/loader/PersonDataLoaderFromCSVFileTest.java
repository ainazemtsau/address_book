package org.anton.loader;

import org.anton.entity.Gender;
import org.anton.entity.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonDataLoaderFromCSVFileTest {

    @Test
    public void testLoadDataFromCSVFile() {
        PersonDataLoaderFromFile loader = new PersonDataLoaderFromCSV("/AddressBook.csv");

        List<Person> loadedPersons = loader.loadData();

        assertEquals(5, loadedPersons.size());

        Person firstPerson = loadedPersons.get(0);
        assertEquals("Bill McKnight", firstPerson.name());
        assertEquals(Gender.MALE, firstPerson.gender());
        assertEquals(LocalDate.of(1977, 3, 16), firstPerson.birthDate());

        Person lastPerson = loadedPersons.get(4);
        assertEquals("Wes Jackson", lastPerson.name());
        assertEquals(Gender.MALE, lastPerson.gender());
        assertEquals(LocalDate.of(1974, 8, 14), lastPerson.birthDate());
    }

    @Test
    public void testLoadDataFromCSVFile_FileNotFound() {
        PersonDataLoaderFromFile loader = new PersonDataLoaderFromCSV("/NonExistentFile.csv");

        List<Person> loadedPersons = loader.loadData();

        assertTrue(loadedPersons.isEmpty());
    }

    @Test
    public void testLoadDataFromCSVFile_EmptyFile() {
        PersonDataLoaderFromFile loader = new PersonDataLoaderFromCSV("/EmptyFile.csv");

        List<Person> loadedPersons = loader.loadData();

        assertTrue(loadedPersons.isEmpty());
    }

    @Test
    public void testLoadDataFromCSVFile_InvalidFormatForFirstPerson() {
        PersonDataLoaderFromFile loader = new PersonDataLoaderFromCSV("/InvalidFormat.csv");

        List<Person> loadedPersons = loader.loadData();

        assertEquals(4, loadedPersons.size());
    }
}