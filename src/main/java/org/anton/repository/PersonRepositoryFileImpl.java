package org.anton.repository;


import org.anton.entity.Person;
import org.anton.loader.PersonDataLoaderFromFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonRepositoryFileImpl implements PersonRepository {

    private final PersonDataLoaderFromFile personDataLoaderFromFile;
    private final Map<String, Person> personMap = new HashMap<>();

    public PersonRepositoryFileImpl(PersonDataLoaderFromFile personDataLoaderFromFile) {
        this.personDataLoaderFromFile = personDataLoaderFromFile;
        loadData();
    }

    private void loadData() {
        List<Person> loadedPersons = personDataLoaderFromFile.loadData();
        if (loadedPersons != null) {
            for (Person person : loadedPersons) {
                personMap.put(person.name(), person);
            }
        }
    }

    @Override
    public List<Person> getAllPerson() {
        return personMap.values().stream().toList();
    }

    @Override
    public Person findById(String id) {
        return personMap.get(id);
    }
}
