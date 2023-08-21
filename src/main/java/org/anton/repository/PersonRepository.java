package org.anton.repository;


import java.util.List;
import org.anton.entity.Person;

public interface PersonRepository {
    List<Person> getAllPerson();

    Person findById(String id);
}
