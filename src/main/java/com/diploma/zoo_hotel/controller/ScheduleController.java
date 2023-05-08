package com.diploma.zoo_hotel.controller;

import com.diploma.zoo_hotel.dto.ScheduleDto;
import com.diploma.zoo_hotel.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Расписание", description = "API для работы с расписание работников")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping(path = "/public/employees/{employeeId}/schedules")
    @Operation(description = "Получить профиль по айди")
    public List<ScheduleDto> getSchedules(@Parameter(description = "Идентификатор работника") @PathVariable Long employeeId) {
        return scheduleService.getScheduleDtosById(employeeId);
    }

    @PostMapping("/employees/{employeeId}/schedules")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создать профиль")
    public void createSchedules(@Parameter(description = "Идентификатор работника") @PathVariable Long employeeId, @Parameter(description = "Данные профиля") @RequestBody List<ScheduleDto> dtos) {
        scheduleService.createSchedules(employeeId, dtos);
    }

    @PutMapping("/employees/{employeeId}/schedules")
    @Operation(description = "Редактировать профиль")
    public List<ScheduleDto> editSchedules(@Parameter(description = "Идентификатор работника") @PathVariable Long employeeId, @Parameter(description = "Данные профиля") @RequestBody List<ScheduleDto> dtos) {
        return scheduleService.editSchedulesDto(employeeId, dtos);
    }

    @DeleteMapping(path = "/employees/{employeeId}/schedules")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить профиль")
    public void deleteSchedules(@Parameter(description = "Идентификатор работника") @PathVariable Long employeeId, @Parameter(description = "Идентификатор профиля") @RequestParam List<Long> ids) {
        scheduleService.deleteSchedule(employeeId, ids);
    }
}
