package com.diploma.zoo_hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Данные пользователя для аутентификации/регистрации
 */
@Data
@Builder
@Validated
@Schema(description = "Данные пользователя для аутентификации/регистрации")
public class AuthDataRequestDto implements Serializable {

    private static final long serialVersionUID = 599053845034850L;

    @NotNull
    @Email
    @Schema(description = "Электронная почта")
    private String email;

    @Schema(description = "Номер телефона")
    private String phoneNumber;

    @NotNull
    @Schema(description = "Пароль")
    private String password;
}
