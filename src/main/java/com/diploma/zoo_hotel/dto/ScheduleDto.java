package com.diploma.zoo_hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Data
@Builder
@Validated
@Schema(description = "Данные о асписание")
public class ScheduleDto {

    @Schema(description = "Идентификатор")
    private Long id;

//    @Schema(description = "Идентификатор")
//    private Long senderId;

    @Schema(description = "Тип услуги")
    private String serviceType;

    @Schema(description = "Дата начала")
    private LocalDateTime startDateTime;

    @Schema(description = "Дата окончания")
    private LocalDateTime endDateTime;
}
