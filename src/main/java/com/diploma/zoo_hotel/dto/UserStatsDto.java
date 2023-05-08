package com.diploma.zoo_hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Статистика по пользователям системы")
public class UserStatsDto {

    @Schema(description = "Всего пользователей в системе")
    private Long allUsers;
}
