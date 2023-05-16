package com.diploma.zoo_hotel.controller;

import com.diploma.zoo_hotel.dto.CustomerDto;
import com.diploma.zoo_hotel.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "Клиент", description = "API для работы с профилями клиентов")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping(path = "/public/customers/{id}")
    @Operation(description = "Получить профиль по айди")
    public CustomerDto getCustomerById(@Parameter(description = "Идентификатор профиля") @PathVariable Long id) {
        return customerService.getCustomerDtoById(id);
    }

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создать профиль")
    public CustomerDto createCustomer(@Parameter(description = "Данные профиля") @RequestBody CustomerDto dto) {
        return customerService.createCustomer(dto);
    }

    @PutMapping("/customers")
    @Operation(description = "Редактировать профиль")
    public CustomerDto editCustomer(@Parameter(description = "Данные профиля") @RequestBody CustomerDto dto) {
        return customerService.editCustomer(dto);
    }

    @DeleteMapping(path = "customer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить профиль")
    public void deleteCustomer(@Parameter(description = "Идентификатор профиля") @PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
