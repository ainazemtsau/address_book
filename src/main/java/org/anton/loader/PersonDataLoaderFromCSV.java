package org.anton.loader;

import org.anton.entity.Gender;
import org.anton.entity.Person;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PersonDataLoaderFromCSV implements PersonDataLoaderFromFile {

    Logger logger = LoggerFactory.getLogger(PersonDataLoaderFromCSV.class);
    private final String csvFilePath;

    public PersonDataLoaderFromCSV(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    @Override
    public List<Person> loadData() {
        logger.info("Loading data from CSV file: {}", csvFilePath);
        List<Person> persons = new ArrayList<>();

        try (InputStream inputStream = getClass().getResourceAsStream(csvFilePath);
             InputStreamReader reader = new InputStreamReader(inputStream)) {

            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            for (CSVRecord record : csvParser) {
                if (record.size() == 3) {
                    String name = record.get(0).trim();
                    Gender gender = switch (record.get(1).trim().toUpperCase()) {
                        case "MALE" -> Gender.MALE;
                        case "FEMALE" -> Gender.FEMALE;
                        default -> throw new IllegalArgumentException("Invalid gender");
                    };

                    LocalDate birthDate = parseBirthDate(record.get(2).trim());

                    persons.add(new Person(name, gender, birthDate));
                }
            }

        } catch (IOException | NullPointerException e) {
            logger.error("Error while loading data from CSV file: {}", e.getMessage());
        }

        return persons;
    }

    private LocalDate parseBirthDate(String birthDateStr) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        int year = Integer.parseInt(birthDateStr.substring(6));
        int century = (year >= 30) ? 1900 : 2000;
        return LocalDate.parse(birthDateStr, dateFormatter).withYear(century + year);
    }
}

