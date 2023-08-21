package org.anton.service;

import org.anton.entity.Person;

public interface AddressService {

    Integer countMales();

    Person findOldestPerson();

    long daysOlder(String person1Name, String person2Name);
}
