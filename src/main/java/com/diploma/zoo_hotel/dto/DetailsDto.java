package com.diploma.zoo_hotel.dto;

import com.diploma.zoo_hotel.entities.Pet;
import com.diploma.zoo_hotel.entities.WeightEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@Builder
@Validated
@Schema(description = "Данные о деталях клиента")
public class DetailsDto {

    @Schema(description = "Идентификатор")
    private Long id;

    @Schema(description = "Опыт работы")
    private Integer experience;

    @Schema(description = "Допустимый размер животного")
    private List<String> acceptSize;

    @Schema(description = "Тип жилья работника")
    private Boolean isHouse;

    @Schema(description = "Количество детей")
    private Integer children;

    @Schema(description = "Наличие безопасной экипировки")
    private Boolean haveEquipment;

    @Schema(description = "Допустимые животные")
    private String acceptAnimals;

    @Schema(description = "Животные работника")
    private List<Long> employeeAnimals;

    @Schema(description = "Наличие вет образования")
    private Boolean haveVetEducation;
}
