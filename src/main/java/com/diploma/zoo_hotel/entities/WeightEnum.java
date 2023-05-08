package com.diploma.zoo_hotel.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum WeightEnum {
    MINI("Мини(до 5кг)"),
    SMALL("Малый(5-10кг)"),
    MEDIUM("Средний(10-20кг)"),
    BIG("Большой(20-40кг"),
    HUGE("огромный(40+кг)");

    private final String name;

    WeightEnum(String name) {
        this.name = name;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(name);
    }

    /**
     * Получить значение енума из строки
     *
     * @param weight строковое представление значения енума (напр. "Ялта")
     *
     * @return значение енума (Cities.YALTA)
     */
    @JsonCreator
    public static WeightEnum fromValue(String weight) {
        return Arrays.stream(WeightEnum.values())
                .filter(weightEnum -> weightEnum.name.equals(weight))
                .findFirst()
                .orElse(null);
    }
}
