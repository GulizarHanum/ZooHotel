package com.diploma.zoo_hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Validated
@Schema(description = "Данные профиля")
public class FilterDto {
    @Schema(description = "Тип услуги")
    private String serviceType;
    @Schema(description = "Город")
    private String city;
    @Schema(description = "Дата начала")
    private LocalDate startDate;
    @Schema(description = "Дата конца")
    private LocalDate endDate;
    @Schema(description = "Максимальная цена")
    private BigDecimal maxCost;
    @Schema(description = "Возвраст животного")
    private Integer agePet;
    @Schema(description = "Вес животного")
    private String weightPet;
    @Schema(description = "Тип жиотного")
    private String animal;
    @Schema(description = "Наличие питомцев у работника")
    private Integer conditionForPetEmployee;
    @Schema(description = "Наличие детей у работника")
    private Boolean hasChildren;
    @Schema(description = "Наличие экипировки для питомцев")
    private Boolean hasEquipment;
    @Schema(description = "Умение делать инъекции, давать препораты")
    private Boolean hasSkillInjections;
    @Schema(description = "Тип жилья")
    private Boolean isHome;
}
