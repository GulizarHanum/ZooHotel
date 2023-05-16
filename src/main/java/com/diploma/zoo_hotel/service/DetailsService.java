package com.diploma.zoo_hotel.service;

import com.diploma.zoo_hotel.dto.DetailsDto;
import com.diploma.zoo_hotel.entities.*;
import com.diploma.zoo_hotel.repository.DetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class DetailsService {
    private final DetailsRepository detailsRepository;
    private final EmployeeService employeeService;

    /**
     * Создание деталей для профиля
     *
     * @param dto объект с данными
     */
    public Details createDetails(Long employeeId, DetailsDto dto) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        Details details = new Details();
        details.setChildren(dto.getChildren());
        details.setExperience(dto.getExperience());
        details.setEmployee(employee);
        details.setHaveEquipment(dto.getHaveEquipment());
        details.setIsHouse(dto.getIsHouse());
        setListField(dto, details);
        return detailsRepository.save(details);
    }

    public DetailsDto createDetailsDto(Long employeeId, DetailsDto dto) {
        return buildDto(createDetails(employeeId, dto));
    }

    /**
     * Получение профиля по айди
     *
     * @param employeeId айди нужного нам профиля
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
     * @param employeeId айди записи
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
     * @return измененный профиль
     */
    public Details editDetails(Long id, DetailsDto newDetails) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            throw new IllegalArgumentException("Неверный идентификатор работника");
        }

        Details detail = getDetailsByEmployee(newDetails.getId());

        detail.setIsHouse(newDetails.getIsHouse());
        detail.setExperience(newDetails.getExperience());
        detail.setChildren(newDetails.getChildren());
        detail.setHaveEquipment(newDetails.getHaveEquipment());
        detail.setEmployee(employee);
        setListField(newDetails, detail);

        return detailsRepository.save(detail);
    }

    public DetailsDto editDetailsDto(Long id, DetailsDto newDetails) {
        return buildDto(editDetails(id, newDetails));
    }

    /**
     * Превратить сущность в ДТО
     *
     * @param details сущность
     * @return ДТО с данными
     */
    public static DetailsDto buildDto(Details details) {
        return DetailsDto.builder()
                .id(details.getId())
                .acceptAnimals(details.getAcceptAnimals() != null
                        ? details.getAcceptAnimals().stream().map(AnimalType::name).collect(Collectors.toList())
                        : List.of())
                .acceptSize(details.getAcceptSize() != null
                        ? details.getAcceptSize().stream().map(Enum::name).collect(Collectors.toList())
                        : List.of())
                .children(details.getChildren())
                .isHouse(details.getIsHouse())
                .haveEquipment(details.getHaveEquipment())
                .haveVetEducation(details.getHaveVetEducation())
                .experience(details.getExperience())
                .haveAnimals(details.getHaveAnimals())
                .build();
    }

    private void setListField(DetailsDto dto, Details details) {
        details.setHaveAnimals(dto.getHaveAnimals());
        details.setAcceptSize(dto.getAcceptSize() != null
                ? dto.getAcceptSize().stream().map(WeightEnum::fromValue).collect(Collectors.toList())
                : List.of());
        details.setAcceptAnimals(dto.getAcceptAnimals() != null
                ? dto.getAcceptAnimals().stream().map(AnimalType::fromValue).collect(Collectors.toList())
                : List.of());
    }
}
