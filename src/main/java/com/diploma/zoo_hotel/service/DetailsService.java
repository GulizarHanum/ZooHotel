package com.diploma.zoo_hotel.service;

import com.diploma.zoo_hotel.dto.DetailsDto;
import com.diploma.zoo_hotel.entities.Details;
import com.diploma.zoo_hotel.entities.Employee;
import com.diploma.zoo_hotel.entities.Pet;
import com.diploma.zoo_hotel.entities.WeightEnum;
import com.diploma.zoo_hotel.repository.DetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class DetailsService {
    private final DetailsRepository detailsRepository;
    private final PetService petService;

    /**
     * Создание деталей для профиля
     *
     * @param dto объект с данными
     */
    public Details createDetails(Long employeeId, DetailsDto dto) {
        Details details = new Details();
        details.setAcceptAnimals(dto.getAcceptAnimals());
        details.setChildren(dto.getChildren());
        details.setEmployeeAnimals(dto.getEmployeeAnimals().get(0) != null
                ? dto.getEmployeeAnimals().stream().map(petService::getPetById).collect(Collectors.toList())
                : null);
        details.setAcceptSize(dto.getAcceptSize().stream().map(WeightEnum::fromValue).collect(Collectors.toList()));
        details.setExperience(dto.getExperience());
        details.setEmployeeId(employeeId);
        details.setHaveEquipment(dto.getHaveEquipment());
        details.setIsHouse(dto.getIsHouse());

        return detailsRepository.save(details);
    }

    /**
     * Получение профиля по айди
     *
     * @param employeeId айди нужного нам профиля
     *
     * @return возвращаем найденный профиль
     */

    public DetailsDto getDetailsDtoByEmployee(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Неверный идентификатор профиля");
        }
        return detailsRepository.findByEmployee(employeeId)
                .map(DetailsService::buildDto)
                .orElse(null);
    }

    /**
     * Возвращает сущность Профиль
     *
     * @param employeeId айди профиля
     *
     * @return сущность Профиль
     */
    public Details getDetailsByEmployee(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Неверный идентификатор профиля");
        }
        return detailsRepository.findByEmployee(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Профиль с id = %s не найден", employeeId)));
    }

    /**
     * Удалить профиль по его идентификатору
     *
     * @param customerId айди записи
     */
    public void deleteDetails(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Неверный идентификатор профиля");
        }

        detailsRepository.deleteByEmployee(employeeId);
    }

    /**
     * Редактировать профиль
     *
     * @param newDetails профиль
     *
     * @return измененный профиль
     */
    public Details editDetails(Long id, DetailsDto newDetails) {
        Details detail = getDetailsByEmployee(newDetails.getId());

        detail.setIsHouse(newDetails.getIsHouse());
        detail.setExperience(newDetails.getExperience());
        detail.setChildren(newDetails.getChildren());
        detail.setHaveEquipment(newDetails.getHaveEquipment());
        detail.setEmployeeId(id);
        detail.setAcceptAnimals(newDetails.getAcceptAnimals());
        detail.setEmployeeAnimals(newDetails.getEmployeeAnimals().get(0) != null
               ? newDetails.getEmployeeAnimals().stream().map(petService::getPetById).collect(Collectors.toList())
               : null);
        detail.setAcceptSize(newDetails.getAcceptSize().stream().map(WeightEnum::fromValue).collect(Collectors.toList()));

        return detailsRepository.save(detail);
    }

    public DetailsDto editDetailsDto(Long id, DetailsDto newDetails) {
        return buildDto(editDetails(id, newDetails));
    }

    /**
     * Превратить сущность в ДТО
     *
     * @param details сущность
     *
     * @return ДТО с данными
     */
    private static DetailsDto buildDto(Details details) {
        return DetailsDto.builder()
                .id(details.getId())
                .acceptAnimals(details.getAcceptAnimals())
                .acceptSize(details.getAcceptSize().stream().map(Enum::name).collect(Collectors.toList()))
                .children(details.getChildren())
                .isHouse(details.getIsHouse())
                .haveEquipment(details.getHaveEquipment())
                .haveVetEducation(details.getHaveVetEducation())
                .experience(details.getExperience())
                .employeeAnimals(details.getEmployeeAnimals().stream().map(Pet::getId).collect(Collectors.toList()))
                .build();
    }
}
