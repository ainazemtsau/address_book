package org.anton.repository;


import java.util.List;
import java.util.Optional;

import org.anton.entity.Person;

public interface PersonRepository {
    List<Person> getAllPerson();

    Optional<Person> findByName(String name);
}
