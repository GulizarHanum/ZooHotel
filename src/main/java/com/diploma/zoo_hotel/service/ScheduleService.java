package com.diploma.zoo_hotel.service;

import com.diploma.zoo_hotel.dto.ScheduleDto;
import com.diploma.zoo_hotel.entities.Employee;
import com.diploma.zoo_hotel.entities.Schedule;
import com.diploma.zoo_hotel.entities.ServiceType;
import com.diploma.zoo_hotel.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final EmployeeService employeeService;

    /**
     * Создание профиля
     *
     * @param dtos объект с данными
     */
    public List<Schedule> createSchedules(Long id, List<ScheduleDto> dtos) {
        List<Schedule> schedules = new ArrayList<>();
        if (!dtos.isEmpty()) {
            Employee employee = employeeService.getEmployeeById(id);
            for (ScheduleDto dto:dtos) {
                Schedule schedule = new Schedule();
                schedule.setServiceType(ServiceType.fromValue(dto.getServiceType()));
                schedule.setSender(employee);
                schedule.setStartDateTime(dto.getStartDateTime());
                schedule.setEndDateTime(dto.getEndDateTime());

                schedules.add(scheduleRepository.save(schedule));
            }
        }
        return schedules;
    }

    public List<ScheduleDto> createScheduleDto(Long id, List<ScheduleDto> dtos) {
        return createSchedules(id, dtos).stream().map(ScheduleService::buildDto).collect(Collectors.toList());
    }

    /**
     * Получение расписания по айди работника
     *
     * @param employeeId айди нужного нам профиля
     *
     * @return возвращаем найденный профиль
     */

    public List<ScheduleDto> getScheduleDtosById(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Неверный идентификатор расписания");
        }
        return scheduleRepository.findAllByEmployeeId(employeeId).stream()
                .map(ScheduleService::buildDto)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает расписание работника
     *
     * @param employeeId айди работника
     *
     * @return сущность Профиль
     */
    public List<Schedule> getScheduleByEmployeeId(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Неверный индетификатор профиля");
        }
        return scheduleRepository.findAllByEmployeeId(employeeId);
    }

    /**
     * Удалить расписание по его идентификатору
     *
     * @param employeeId айди работника
     */
    public void deleteSchedule(Long employeeId, List<Long> ids) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Неверный идентификатор работника");
        }
        ids.forEach(scheduleId -> scheduleRepository.deleteByIds(scheduleId, employeeId));
    }

    /**
     * Сохранить профиль
     *
     * @param schedules сущность Расписание
     */
    public void saveSchedules(List<Schedule> schedules) {
        if (schedules == null) {
            throw new IllegalArgumentException("Передан пустой профиль");
        }

        scheduleRepository.saveAll(schedules);
    }

    /**
     * Редактировать профиль
     *
     * @param newSchedules профиль
     *
     * @return измененный профиль
     */
    public List<Schedule> editSchedules(Long employeeId, List<ScheduleDto> newSchedules) {
        List<Schedule> schedules = scheduleRepository.findAllByEmployeeId(employeeId);

        List<Schedule> dtos = new ArrayList<>();
        for (ScheduleDto schedule : newSchedules) {
            schedules.forEach(sch -> {
                if (sch.getId().equals(schedule.getId())) {
                    sch.setServiceType(ServiceType.fromValue(schedule.getServiceType()));
                    sch.setStartDateTime(schedule.getStartDateTime());
                    sch.setEndDateTime(schedule.getEndDateTime());
                    dtos.add(scheduleRepository.save(sch));
                }
            });
        }

        return dtos;
    }

    public List<ScheduleDto> editSchedulesDto(Long employeeId, List<ScheduleDto> newSchedules) {
        return editSchedules(employeeId, newSchedules).stream().map(ScheduleService::buildDto).collect(Collectors.toList());
    }

    /**
     * Превратить сущность в ДТО
     *
     * @param schedule сущность
     *
     * @return ДТО с данными
     */
    private static ScheduleDto buildDto(Schedule schedule) {
        return ScheduleDto.builder()
                .id(schedule.getId())
                .startDateTime(schedule.getStartDateTime())
                .endDateTime(schedule.getEndDateTime())
                .serviceType(schedule.getServiceType().name())
                .build();
    }
}
