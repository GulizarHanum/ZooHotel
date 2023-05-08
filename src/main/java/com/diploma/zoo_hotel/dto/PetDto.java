package com.diploma.zoo_hotel.dto;

import com.diploma.zoo_hotel.entities.Details;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Validated
@Schema(description = "Данные о питомце")
public class PetDto {
    private Long id;

    private String photo;

    private Long ownerId;

//    private Long detailsId;

    private String name;

    private String animalType;

    private Boolean haveAllVaccinations;

    private BigDecimal weight;

    private Boolean isFemale;

    private LocalDate birthDate;

    private String description;

//    private List<Property> properties;
}
