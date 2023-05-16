package com.diploma.zoo_hotel.dto;

import com.diploma.zoo_hotel.entities.City;
import com.diploma.zoo_hotel.entities.CostServiceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Validated
@Schema(description = "Данные профиля")
public class EmployeeDto {
    @Schema(description = "Идентификатор")
    private Long id;

    @Schema(description = "Идентификатор пользователя, которому принадлежит профиль")
    private Long userId;

    @Schema(description = "Фамилия")
    private String lastName;

    @NotNull
    @Schema(description = "Имя")
    private String firstName;

    @Schema(description = "Дата рождения")
    private LocalDate birthDate;

    @Schema(description = "Дата рождения")
    private String description;

    @Schema(description = "Фото")
    private String photo;

    @Schema(description = "Фотографии")
    private List<String> photos;

    @Schema(description = "Рейтинг")
    private BigDecimal rating;

    @Schema(description = "Цены за услуги")
    private List<CostServiceTypeDto> costs;

    @Schema(description = "Расписание")
    private List<ScheduleDto> schedule;

    @Schema(description = "Доп инфо")
    private DetailsDto details;

    @NotNull
    @Schema(description = "Город")
    private City city;

    @Schema(description = "Улица")
    private String street;

    @Schema(description = "Дом")
    private String house;
}
