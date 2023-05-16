package com.diploma.zoo_hotel.service;

import com.diploma.zoo_hotel.dto.EmployeeDto;
import com.diploma.zoo_hotel.dto.FilterDto;
import com.diploma.zoo_hotel.entities.*;
import com.diploma.zoo_hotel.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.diploma.zoo_hotel.entities.ServiceType.*;
import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserService userService;
    private final CostServiceTypeService typeService;
//    private final DetailsService detailsService;

    /**
     * Создание профиля
     *
     * @param dto объект с данными
     */
    public EmployeeDto createEmployee(EmployeeDto dto) {
        User user = userService.findUserById(dto.getUserId());

        Employee employee = new Employee();
        employee.setLastName(dto.getLastName());
        employee.setFirstName(dto.getFirstName());
        employee.setPhoto(UtilsService.convertPhotoToByte(dto.getPhoto()));
        employee.setRating(BigDecimal.ZERO);
        employee.setAddress(getAddress(dto));
        employee.setBirthDate(dto.getBirthDate());
        employee.setCreationDateTime(LocalDateTime.now());
        employee.setUser(user);
        employee.setDescription(dto.getDescription());
//        employee.setDetails(new Details());

        Employee empl = employeeRepository.save(employee);
        empl.setCosts(typeService.createCostServiceType(empl.getId(), dto.getCosts()));
//        empl.setDetails(detailsService.createDetails(empl.getId(), dto.getDetails()));

        return buildDto(employeeRepository.save(empl));
    }

    /**
     * Получение профиля по айди
     *
     * @param id айди нужного нам профиля
     * @return возвращаем найденный профиль
     */

    public EmployeeDto getEmployeeDtoById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Неверный идентификатор профиля");
        }
        return employeeRepository.findById(id)
                .map(EmployeeService::buildDto)
                .orElse(null);
    }

    /**
     * Возвращает сущность Профиль
     *
     * @param id айди профиля
     * @return сущность Профиль
     */
    public Employee getEmployeeById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Неверный идентификатор профиля");
        }
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Профиль с id = %s не найден", id)));
    }

    /**
     * Удалить профиль по его идентификатору
     *
     * @param employeeId айди записи
     */
    public void deleteEmployee(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Неверный идентификатор профиля");
        }

        employeeRepository.deleteById(employeeId);
    }

    /**
     * Сохранить профиль
     *
     * @param employee сущность Профиль
     */
    public void saveEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Передан пустой профиль");
        }

        employeeRepository.save(employee);
    }

    /**
     * Редактировать профиль
     *
     * @param newEmployee профиль
     * @return измененный профиль
     */
    public EmployeeDto editEmployee(EmployeeDto newEmployee) {
        Employee employeeRecord = getEmployeeById(newEmployee.getId());

        employeeRecord.setLastName(newEmployee.getLastName());
        employeeRecord.setFirstName(newEmployee.getFirstName());
        employeeRecord.setAddress(getAddress(newEmployee));
        employeeRecord.setPhoto(UtilsService.convertPhotoToByte(newEmployee.getPhoto()));
//        employeeRecord.setDetails(detailsService.editDetails(newEmployee.getId(), newEmployee.getDetails()));
        employeeRecord.setCosts(typeService.editCostServiceTypeDto(newEmployee.getId(), newEmployee.getCosts()));

        return buildDto(employeeRepository.save(employeeRecord));
    }

    /**
     * Обновить среднее значение оценки профиля
     *
     * @param employeeId айди профиля
     * @param newAvgMark новое среднее значение
     */
    public void updateAvgMark(Long employeeId, BigDecimal newAvgMark) {
        Employee employee = this.getEmployeeById(employeeId);
        employee.setRating(newAvgMark);
        this.saveEmployee(employee);
    }

    public List<EmployeeDto> search(FilterDto dto) {
        ServiceType type = ServiceType.fromValue(dto.getServiceType());
        Iterable<Employee> employees = employeeRepository.findAll();
        List<EmployeeDto> dtos = new ArrayList<>();
        switch (type) {
            case WALKING:
                for (Employee item : employees) {
                    if (filter(item, dto, WALKING)) {
                        dtos.add(buildDto(item));
                    }
                }
            case DAY_NANNY:
                for (Employee item : employees) {
                    if (filter(item, dto, DAY_NANNY)
                            && item.getDetails().getHaveVetEducation().equals(dto.getHasSkillInjections())) {
                        dtos.add(buildDto(item));
                    }
                }
            case TEMPORARY:
                for (Employee item : employees) {
                    if (filter(item, dto, TEMPORARY) && isAvailableCountPets(item, dto.getConditionForPetEmployee())) {
                        dtos.add(buildDto(item));
                    }
                }
        }
        return dtos;
    }

    private boolean isAvailableCountPets(Employee item, Integer type) {
        boolean haveAnimals = item.getDetails().getHaveAnimals();
        switch (type) {
            case 1:
                return !haveAnimals;
            case 2:
                return haveAnimals;
            default:
                return true;
        }
    }

    private boolean filter(Employee item, FilterDto dto, ServiceType type) {
        return nonNull(item)
                && item.getAddress().getCity().equals(City.fromValue(dto.getCity()))
                && item.getDetails().getHaveEquipment().equals(dto.getHasEquipment())
                && isFreeDays(item.getSchedule(), dto.getStartDate(), dto.getEndDate())
                && isAvailableCost(item.getCosts(), type, dto.getMaxCost())
                && item.getDetails().getAcceptSize().contains(WeightEnum.valueOf(dto.getWeightPet()))
                && item.getDetails().getAcceptAnimals().contains(dto.getAnimal());
    }

    private boolean isAvailableCost(List<CostServiceType> costs, ServiceType type, BigDecimal maxCost) {
        return costs.stream().noneMatch(cost -> cost.getServiceType() == type && cost.getCost().min(maxCost).equals(maxCost));
    }

    private boolean isFreeDays(List<Schedule> schedules, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atStartOfDay();
        List<Boolean> notFreeDates = schedules.stream().map(schedule -> {
            LocalDateTime start = schedule.getStartDateTime();
            LocalDateTime end = schedule.getEndDateTime();
            return (end.isBefore(startDateTime) && end.isBefore(endDateTime))
                    || (start.isAfter(startDateTime) && start.isAfter(endDateTime));
        }).collect(Collectors.toList());
        return notFreeDates.contains(true);
    }

    /**
     * Превратить сущность в ДТО
     *
     * @param employee сущность
     * @return ДТО с данными
     */
    private static EmployeeDto buildDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .city(employee.getAddress().getCity())
                .street(employee.getAddress().getStreet())
                .house(employee.getAddress().getHouse())
                .photo(UtilsService.convertPhotoToString(employee.getPhoto()))
                .rating(employee.getRating())
                .userId(employee.getUser().getId())
                .details(employee.getDetails() != null ? DetailsService.buildDto(employee.getDetails()) : null)
                .costs(employee.getCosts().stream().map(CostServiceTypeService::buildDto).collect(Collectors.toList()))
                .build();
    }

    private Address getAddress(EmployeeDto dto) {
        return Address.builder()
                .city(dto.getCity())
                .street(dto.getStreet())
                .house(dto.getHouse())
                .build();
    }
}
