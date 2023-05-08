package com.diploma.zoo_hotel.controller;

import com.diploma.zoo_hotel.dto.DetailsDto;
import com.diploma.zoo_hotel.service.DetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "Детали работника", description = "API для работы с деталями работника")
public class DetailsController {
    private final DetailsService detailsService;

    @GetMapping(path = "/public/employee/{employeeId}/details")
    @Operation(description = "Получить профиль по айди")
    public DetailsDto getDetailsDtoByEmployee(@Parameter(description = "Идентификатор работника") @PathVariable Long employeeId) {
        return detailsService.getDetailsDtoByEmployee(employeeId);
    }

    @PostMapping("/employee/{employeeId}/details")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создать профиль")
    public void createDetails(@Parameter(description = "Идентификатор Работника") @PathVariable Long employeeId, @Parameter(description = "Доп данные работника") @RequestBody DetailsDto dto) {
        detailsService.createDetails(employeeId, dto);
    }

    @PutMapping("/employee/{employeeId}/details")
    @Operation(description = "Редактировать профиль")
    public DetailsDto editDetails(@Parameter(description = "Идентификатор Работника") @PathVariable Long employeeId, @Parameter(description = "Доп данные работника") @RequestBody DetailsDto dto) {
        return detailsService.editDetailsDto(employeeId, dto);
    }
}
