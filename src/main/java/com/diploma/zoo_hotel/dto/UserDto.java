package com.diploma.zoo_hotel.dto;

import com.diploma.zoo_hotel.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@Validated
@Schema(description = "Данные пользователя")
public class UserDto {

    @Schema(description = "Идентификатор")
    private Long id;

    @Email
    @NotNull
    @Schema(description = "Электронная почта")
    private String email;

    @NotNull
    @Schema(description = "Никнейм")
    private String username;

    @NotNull
    @Schema(description = "Пароль")
    private String password;

    @Schema(description = "Номер телефона")
    private String phoneNumber;

    @Schema(description = "Признак активности пользователя")
    private boolean active;

    @Schema(description = "Список ролей")
    private Set<User.Role> roles;
}
