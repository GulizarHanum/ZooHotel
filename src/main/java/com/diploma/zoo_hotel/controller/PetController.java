package com.diploma.zoo_hotel.controller;

import com.diploma.zoo_hotel.dto.PetDto;
import com.diploma.zoo_hotel.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "Питомец", description = "API для работы с питомцами клиентов")
public class PetController {
    private final PetService petService;

    @GetMapping(path = "/public/pets/{id}")
    @Operation(description = "Получить питомца по айди")
    public PetDto getPetById(@Parameter(description = "Идентификатор питомца") @PathVariable Long id) {
        return petService.getPetDtoById(id);
    }

    @PostMapping("/pets")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создать питомца")
    public PetDto createPet(@Parameter(description = "Данные питомца") @RequestBody PetDto dto) {
        return petService.createPet(dto);
    }

    @PutMapping("/pets")
    @Operation(description = "Редактировать питомца")
    public PetDto editPet(@Parameter(description = "Данные питомца") @RequestBody PetDto dto) {
        return petService.editPetDto(dto);
    }

    @DeleteMapping(path = "pets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить питомца")
    public void deletePet(@Parameter(description = "Идентификатор питомца") @PathVariable Long id) {
        petService.deletePet(id);
    }
}
