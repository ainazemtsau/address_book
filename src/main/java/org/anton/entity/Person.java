package org.anton.entity;

import java.time.LocalDate;

public record Person(String name, Gender gender, LocalDate birthDate) {
}
