package com.diploma.zoo_hotel.dto;

import com.diploma.zoo_hotel.entities.Customer;
import com.diploma.zoo_hotel.entities.Employee;
import com.diploma.zoo_hotel.entities.ServiceType;
import com.diploma.zoo_hotel.entities.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Validated
@Schema(description = "Данные об отзыве")
public class OrderDto {

    @Schema(description = "Идентификатор отзыва")
    private Long id;

    @Schema(description = "Идентификатор отзыва")
    private BigDecimal cost;

    @Schema(description = "Идентификатор отзыва")
    private String description;

    @Schema(description = "Идентификатор отзыва")
    private LocalDateTime createDateTime;

    @Schema(description = "Идентификатор отзыва")
    private LocalDateTime startDateTime;

    @Schema(description = "Идентификатор отзыва")
    private LocalDateTime endDateTime;

    @Schema(description = "Идентификатор отзыва")
    private String status;

    @Schema(description = "Идентификатор отзыва")
    private Long sellerId;

    @Schema(description = "Идентификатор отзыва")
    private Long customerId;

    @Schema(description = "Идентификатор отзыва")
    private String serviceType;
}
