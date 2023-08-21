package org.anton;

import org.anton.loader.PersonDataLoaderFromCSV;
import org.anton.service.AddressService;
import org.anton.service.AddressServiceImpl;
import org.anton.repository.PersonRepository;
import org.anton.repository.PersonRepositoryFileImpl;
import org.anton.entity.Person;
import org.anton.loader.PersonDataLoaderFromFile;

public class Main {

    private static AddressService addressService;

    public static void main(String[] args) {
        prepareData();

        int maleCount = addressService.countMales();
        Person oldestPerson = addressService.findOldestPerson();
        long daysDifference = addressService.daysOlder("Bill", "Paul");


        System.out.println("--------- Address Book Insights ---------");
        System.out.println();
        System.out.printf("1. Number of males in the address book: %d%n", maleCount);
        System.out.println();

        if (oldestPerson != null) {
            System.out.printf("2. The oldest person in the address book is: %s (Birthdate: %s)%n",
                    oldestPerson.name(), oldestPerson.birthDate());
        } else {
            System.out.println("2. No persons found in the address book.");
        }
        System.out.println();

        if (daysDifference < 0) {
            System.out.printf("3. Paul is %d days older than Bill.%n", Math.abs(daysDifference));
        } else {
            System.out.printf("3. Bill is %d days older than Paul.%n", daysDifference);
        }
        System.out.println("----------------------------------------");
    }

    private static void prepareData() {
        PersonDataLoaderFromFile dataLoader = new PersonDataLoaderFromCSV("/AddressBook.csv");
        PersonRepository repository = new PersonRepositoryFileImpl(dataLoader);

        addressService = new AddressServiceImpl(repository);
    }
}
