package com.diploma.zoo_hotel.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Сущность "Адрес" для указания адреса в объявлении
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Embeddable
@Builder
public class Address {

    @Enumerated(EnumType.STRING)
    @Column(name = "city")
    private City city;

    @Column(name = "street")
    private String street;

    @Column(name = "house")
    private String house;
}
