package com.diploma.zoo_hotel.controller;

import com.diploma.zoo_hotel.dto.EmployeeDto;
import com.diploma.zoo_hotel.dto.FilterDto;
import com.diploma.zoo_hotel.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Работник", description = "API для работы с профилями работников")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping(path = "/public/employees/{id}")
    @Operation(description = "Получить профиль по айди")
    public EmployeeDto getEmployeeById(@Parameter(description = "Идентификатор профиля") @PathVariable Long id) {
        return employeeService.getEmployeeDtoById(id);
    }

    @GetMapping(path = "/public/employees")
    @Operation(description = "Получить профили по фильтру")
    public List<EmployeeDto> getEmployees(@Parameter(description = "Фильтр поиска") @RequestBody FilterDto dto) {
        return employeeService.search(dto);
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создать профиль")
    public void createEmployee(@Parameter(description = "Данные профиля") @RequestBody EmployeeDto dto) {
        employeeService.createEmployee(dto);
    }

    @PutMapping("/employees")
    @Operation(description = "Редактировать профиль")
    public EmployeeDto editEmployee(@Parameter(description = "Данные профиля") @RequestBody EmployeeDto dto) {
        return employeeService.editEmployee(dto);
    }

    @DeleteMapping(path = "employee/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить профиль")
    public void deleteEmployee(@Parameter(description = "Идентификатор профиля") @PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}
