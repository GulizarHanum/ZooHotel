package com.diploma.zoo_hotel.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

/**
 * Список возможных животных
 */
public enum AnimalType {
    DOG("Собака"),
    CAT("Кот"),
    RODENT("Грызун"),
    BIRD("Птица"),
    INSECT("Насекомое"),
    REPTILE("Рептилия"),
    FISH("Рыба"),
    ANOTHER("Другое");

    private final String name;

    AnimalType(String animal) {
        this.name = animal;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(name);
    }

    @JsonCreator
    public static AnimalType fromValue(String text) {
        return Arrays.stream(AnimalType.values())
                .filter(animal -> animal.name.equals(text))
                .findFirst()
                .orElse(null);
    }
}
