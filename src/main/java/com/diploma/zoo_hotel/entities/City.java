package com.diploma.zoo_hotel.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

/**
 * Список городов
 */
public enum City {
    SEVASTOPOL("Севастополь"),
    SIMFEROPOL("Симферополь"),
    YALTA("Ялта"),
    ALUSHTA("Алушта"),
    FEODOSIA("Феодосия"),
    KERCH("Керчь"),
    DZHANKOI("Джанкой"),
    KRASNOPEREKOPSK("Красноперекопск"),
    EVPATORIA("Евпатория"),
    SAKI("Саки"),
    SUDAK("Судак"),
    BAKHCHISARAI("Бахчисарай"),
    ALUPKA("Алупка"),
    INKERMAN("Инкерман"),
    BELOGORSK("Белогорск"),
    BALACLAVA("Балаклава");

    private final String name;

    City(String name) {
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
     * @param text строковое представление значения енума (напр. "Ялта")
     *
     * @return значение енума (Cities.YALTA)
     */
    @JsonCreator
    public static City fromValue(String text) {
        return Arrays.stream(City.values())
                .filter(candidate -> candidate.name.equals(text))
                .findFirst()
                .orElse(null);
    }
}
