package com.diploma.zoo_hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

/**
 * Ответ при аутентификации пользователя в системе
 */
@Getter
@AllArgsConstructor
@Schema(description = "Токены аутентификации пользователя в системе")
public class AuthTokensDto implements Serializable {
    private static final long serialVersionUID = -4353435345353L;

    @Schema(description = "Токен аутентификации")
    private String accessToken;

    @Schema(description = "Refresh-токен")
    private UUID refreshToken;

    @Schema(description = "Время деактивации токена аутентификации")
    private long expiresIn;
}
