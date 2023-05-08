package com.diploma.zoo_hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Validated
@Schema(description = "Данные профиля")
public class CustomerDto {

    @Schema(description = "Идентификатор")
    private Long id;

    @Schema(description = "Идентификатор пользователя, которому принадлежит профиль")
    private Long userId;

    @NotNull
    @Schema(description = "Фамилия")
    private String lastName;

    @NotNull
    @Schema(description = "Имя")
    private String firstName;

    @Schema(description = "Фото")
    private String photo;

    @Schema(description = "Дата рождения")
    private LocalDate birthDate;

    @NotNull
    @Schema(description = "Город")
    private String city;

}
