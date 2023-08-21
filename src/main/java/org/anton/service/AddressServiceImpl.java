package org.anton.service;


import org.anton.entity.Gender;
import org.anton.entity.Person;
import org.anton.repository.PersonRepository;

import java.time.temporal.ChronoUnit;
import java.util.Comparator;
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
        var person1 = personRepository.findByName(person1Name);
        var person2 = personRepository.findByName(person2Name);

        if (person1.isEmpty() || person2.isEmpty()) {
            throw new IllegalArgumentException("One or both persons not found.");
        }

        return ChronoUnit.DAYS.between(person1.get().birthDate(), person2.get().birthDate());
    }
}
