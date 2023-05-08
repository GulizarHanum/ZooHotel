package com.diploma.zoo_hotel.controller;


import com.diploma.zoo_hotel.dto.AuthDataRequestDto;
import com.diploma.zoo_hotel.dto.AuthTokensDto;
import com.diploma.zoo_hotel.exceptions.BadAuthCredentialsException;
import com.diploma.zoo_hotel.service.AccessTokenProvider;
import com.diploma.zoo_hotel.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

/**
 * Контроллер аутентификации
 */
@RestController
@AllArgsConstructor
@Tag(name = "Аутентификация", description = "API модуля аутентификации в системе")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AccessTokenProvider accessTokenProvider;
    private final UserService userService;

    @PostMapping("/public/auth/login")
    @Operation(description = "Аутентификация пользователя")
    public AuthTokensDto login(@RequestBody AuthDataRequestDto loginData) {
        String email = loginData.getEmail();
        authenticate(email, loginData.getPassword());

        return accessTokenProvider.generateAllTokens(email);
    }

    @PostMapping("/public/auth/refresh")
    @Operation(description = "Получение новых токенов")
    public AuthTokensDto refreshToken(@RequestBody AuthTokensDto refreshTokenData) {
        UUID refreshToken = refreshTokenData.getRefreshToken();

        return accessTokenProvider.regenerateAllTokens(refreshToken);
    }

    @PostMapping("/auth/logout")
    @Operation(description = "Выход из системы")
    public void logout(@RequestBody AuthTokensDto refreshTokenData) {
        UUID refreshToken = refreshTokenData.getRefreshToken();
        accessTokenProvider.logoutUser(refreshToken);
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new BadAuthCredentialsException("Пользователь неактивен");
        } catch (BadCredentialsException e) {
            throw new BadAuthCredentialsException("Введен некорректный логин/пароль.");
        }
    }
}
