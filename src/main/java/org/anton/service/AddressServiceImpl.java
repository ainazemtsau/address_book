package org.anton.service;


import org.anton.entity.Gender;
import org.anton.entity.Person;
import org.anton.repository.PersonRepository;

import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AddressServiceImpl implements AddressService {

    private final PersonRepository personRepository;

    public AddressServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Integer countMales() {
        return personRepository.getAllPerson().stream().filter(p -> p.gender() == Gender.MALE).toList().size();
    }

    @Override
    public Person findOldestPerson() {
        var allPersons = personRepository.getAllPerson();
        Optional<Person> oldestPerson = allPersons.stream()
                .min(Comparator.comparing(Person::birthDate));
        return oldestPerson.orElse(null);
    }

    @Override
    public long daysOlder(String person1Name, String person2Name) {
        var person1 = personRepository.findById(person1Name);
        var person2 = personRepository.findById(person2Name);

        if (person1 == null || person2 == null) {
            throw new IllegalArgumentException("One or both persons not found.");
        }

        return ChronoUnit.DAYS.between(person1.birthDate(), person2.birthDate());
    }
}
