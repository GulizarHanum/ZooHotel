package com.diploma.zoo_hotel.dto;

import com.diploma.zoo_hotel.entities.ServiceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Data
@Builder
@Validated
@Schema(description = "Данные профиля")
public class CostServiceTypeDto {
    @Schema(description = "Идентификатор")
    private Long id;
//    @Schema(description = "Идентификатор")
//    private Long employeeId;
    @Schema(description = "Тип услуги")
    private String serviceType;
    @Schema(description = "Цена")
    private BigDecimal cost;
}
