package com.diploma.zoo_hotel.controller;


import com.diploma.zoo_hotel.dto.AuthDataRequestDto;
import com.diploma.zoo_hotel.dto.UserDto;
import com.diploma.zoo_hotel.dto.UserStatsDto;
import com.diploma.zoo_hotel.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.diploma.zoo_hotel.configuration.SecurityConfig.DELETE_ENDPOINT;
import static com.diploma.zoo_hotel.configuration.SecurityConfig.REGISTER_ADMIN_ENDPOINT;

/**
 * Контроллер пользователей
 */
@RestController
@AllArgsConstructor
@Tag(name = "Пользователи", description = "API управления пользователями системы")
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/public/users/stats")
    @Operation(description = "Получить статистику по пользователям в системе")
    public UserStatsDto getUsersStats() {
        return userService.getUsersStats();
    }

    @PostMapping("/public/users/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Регистрация пользователя")
    public void registerUser(@RequestBody AuthDataRequestDto registerData) {
        userService.createUser(registerData, false);
    }

    @PostMapping(REGISTER_ADMIN_ENDPOINT)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Регистрация администратора")
    public void registerAdmin(@RequestBody AuthDataRequestDto registerData) {
        userService.createUser(registerData, true);
    }

    @PatchMapping("/users")
    @Operation(description = "Редактирование пользователя")
    public UserDto editUserDto(@RequestBody UserDto user) {
        return userService.editUserDto(user);
    }

    @DeleteMapping(DELETE_ENDPOINT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удаление пользователя")
    public void deleteUser(@RequestBody Long id) {
        userService.deleteUser(id);
    }

}
