package com.diploma.zoo_hotel.service;

import com.diploma.zoo_hotel.dto.CostServiceTypeDto;
import com.diploma.zoo_hotel.entities.CostServiceType;
import com.diploma.zoo_hotel.entities.Employee;
import com.diploma.zoo_hotel.entities.ServiceType;
import com.diploma.zoo_hotel.repository.CostServiceTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CostServiceTypeService {
    private final CostServiceTypeRepository typeRepository;

    /**
     * Создание профиля
     *
     * @param dtos объект с данными
     */
    public List<CostServiceType> createCostServiceType(Long id, List<CostServiceTypeDto> dtos) {
        if (id == null) {
            throw new IllegalArgumentException("Неверный индетификатор работника");
        }
        return dtos.stream().map(dto -> {
            CostServiceType type = new CostServiceType();
            type.setServiceType(ServiceType.fromValue(dto.getServiceType()));
            type.setCost(dto.getCost());
            type.setEmployeeId(id);

            return typeRepository.save(type);
        }).collect(Collectors.toList());
    }

    /**
     * Получение профиля по айди
     *
     * @param id айди нужного нам профиля
     * @return возвращаем найденный профиль
     */

    public List<CostServiceTypeDto> getCostServiceTypeDtoByEmployee(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Неверный идентификатор профиля");
        }
        return typeRepository.findByEmployee(id)
                .stream()
                .map(CostServiceTypeService::buildDto)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает сущность Профиль
     *
     * @param id айди профиля
     * @return сущность Профиль
     */
    public List<CostServiceType> getCostServiceTypeByEmployee(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Неверный идентификатор профиля");
        }
        return typeRepository.findByEmployee(id);
    }

    /**
     * Удалить профиль по его идентификатору
     *
     * @param employeeId айди записи
     */
    public void deleteCostServiceType(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Неверный идентификатор профиля");
        }

        typeRepository.deleteByEmployee(employeeId);
    }


    /**
     * Редактировать профиль
     *
     * @param newTypes профиль
     * @return измененный профиль
     */
    public List<CostServiceType> editCostServiceTypeDto(Long id, List<CostServiceTypeDto> newTypes) {
        return newTypes.stream().map(newType -> {
            CostServiceType type = typeRepository.findByEmployee(id, newType.getId());
            type.setServiceType(ServiceType.fromValue(newType.getServiceType()));
            type.setCost(newType.getCost());
            return typeRepository.save(type);
        }).collect(Collectors.toList());
    }

    /**
     * Превратить сущность в ДТО
     *
     * @param costServiceType сущность
     * @return ДТО с данными
     */
    private static CostServiceTypeDto buildDto(CostServiceType costServiceType) {
        return CostServiceTypeDto.builder()
                .id(costServiceType.getId())
                .cost(costServiceType.getCost())
                .serviceType(costServiceType.getServiceType().name())
                .build();
    }
}
