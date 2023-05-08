package com.diploma.zoo_hotel.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ServiceType {

    TEMPORARY("Передержка"),
    DAY_NANNY("Дневная няня"),
    WALKING("Выгул");

    private final String name;

    ServiceType(String name) {
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
     * @param serviceType строковое представление значения енума (напр. "Ялта")
     *
     * @return значение енума (Cities.YALTA)
     */
    @JsonCreator
    public static ServiceType fromValue(String serviceType) {
        return Arrays.stream(ServiceType.values())
                .filter(type -> type.name.equals(serviceType))
                .findFirst()
                .orElse(null);
    }
}
