package org.anton.service;

import org.anton.entity.Gender;
import org.anton.entity.Person;
import org.anton.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddressServiceImplTest {

    public static final String BILL = "Bill";
    public static final String PAUL = "Paul";
    private PersonRepository personRepository;
    private AddressService addressService;

    @BeforeEach
    public void setUp() {
        personRepository = mock(PersonRepository.class);
        addressService = new AddressServiceImpl(personRepository);

        when(personRepository.getAllPerson()).thenReturn(defaultPersonList());
    }

    private List<Person> defaultPersonList() {
        return List.of(
                new Person("Person 1", Gender.MALE, LocalDate.of(1977, 3, 16)),
                new Person("Person 2", Gender.MALE, LocalDate.of(1985, 1, 15)),
                new Person("Person 3", Gender.MALE, LocalDate.of(1991, 11, 20)),
                new Person("Person 4", Gender.FEMALE, LocalDate.of(1980, 9, 20)),
                new Person("Person 5", Gender.FEMALE, LocalDate.of(1977, 3, 16))
        );
    }

    @Test
    public void givenDefaultList_whenCountMales_shouldReturn3() {
        assertEquals(3, addressService.countMales());
    }

    @Test
    public void givenEmptyList_whenCountMales_shouldReturn0() {
        when(personRepository.getAllPerson()).thenReturn(List.of());
        assertEquals(0, addressService.countMales());
    }

    @Test
    public void givenAllFemales_whenCountMales_shouldReturn0() {
        when(personRepository.getAllPerson()).thenReturn(List.of(
                new Person("Person 1", Gender.FEMALE, LocalDate.now()),
                new Person("Person 2", Gender.FEMALE, LocalDate.now())
        ));
        assertEquals(0, addressService.countMales());
    }

    @Test
    public void givenEmptyList_whenFindOldestPerson_shouldReturnNull() {
        when(personRepository.getAllPerson()).thenReturn(List.of());
        assertNull(addressService.findOldestPerson());
    }

    @Test
    public void givenTwoPersons_whenCalculateAgeDifference_shouldReturnCorrectDifference() {
        Person person1 = new Person(BILL, Gender.MALE, LocalDate.of(1984, 1, 2));
        Person person2 = new Person(PAUL, Gender.MALE, LocalDate.of(1985, 1, 1));

        when(personRepository.findByName(BILL)).thenReturn(Optional.of(person1));
        when(personRepository.findByName(PAUL)).thenReturn(Optional.of(person2));

        long daysDifference = addressService.daysOlder(BILL, PAUL);

        assertEquals(365, daysDifference);
    }

    @Test
    public void givenOnePersonDoesNotExist_whenCalculateAgeDifference_shouldHandleGracefully() {
        Person person1 = new Person(BILL, Gender.MALE, LocalDate.of(1980, 1, 1));

        when(personRepository.findByName(BILL)).thenReturn(Optional.of(person1));
        when(personRepository.findByName(PAUL)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> addressService.daysOlder(BILL, PAUL));
    }
}
