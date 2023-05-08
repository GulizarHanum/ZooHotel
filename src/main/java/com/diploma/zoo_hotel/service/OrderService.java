package com.diploma.zoo_hotel.service;

import com.diploma.zoo_hotel.dto.OrderDto;
import com.diploma.zoo_hotel.dto.ScheduleDto;
import com.diploma.zoo_hotel.entities.*;
import com.diploma.zoo_hotel.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Логика для работы с заказами
 */
@Service
@AllArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final ScheduleService scheduleService;

    @Transactional
    public void createOrder(OrderDto dto) {
        Customer customer = customerService.getCustomerById(dto.getCustomerId());
        Employee seller = employeeService.getEmployeeById(dto.getSellerId());

        Order order = new Order();
        order.setId(dto.getId());
        order.setSeller(seller);
        order.setCustomer(customer);
        order.setCost(dto.getCost());
        order.setCreateDateTime(LocalDateTime.now());
        order.setServiceType(ServiceType.fromValue(dto.getServiceType()));
        order.setDescription(dto.getDescription());
        order.setStartDateTime(order.getStartDateTime());
        order.setStatus(Status.NEW);
        order.setEndDateTime(order.getEndDateTime());
        orderRepository.save(order);

        scheduleService.createSchedules(dto.getSellerId(),
                List.of(ScheduleDto.builder()
                .serviceType(dto.getServiceType())
                .startDateTime(dto.getStartDateTime())
                .endDateTime(dto.getEndDateTime())
                .build()));
    }

    public List<OrderDto> getOrdersByCustomer(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Неверный идентификатор профиля");
        }
        return orderRepository.findByCustomer(id).stream().map(OrderService::buildDto).collect(Collectors.toList());
    }

    public List<OrderDto> getOrdersByEmployee(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Неверный идентификатор профиля");
        }
        return orderRepository.findBySeller(id).stream().map(OrderService::buildDto).collect(Collectors.toList());
    }

    public Order getOrderById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Неверный идентификатор профиля");
        }
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Заказ с id = %s не найден", id)));
    }

    public OrderDto update(OrderDto dto) {
        Order order = getOrderById(dto.getId());

        order.setId(dto.getId());
        order.setCost(dto.getCost());
        order.setServiceType(ServiceType.fromValue(dto.getServiceType()));
        order.setDescription(dto.getDescription());
        order.setStartDateTime(order.getStartDateTime());
        order.setStatus(Status.valueOf(dto.getStatus()));
        order.setEndDateTime(order.getEndDateTime());
        orderRepository.save(order);
        return buildDto(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    /**
     * Превратить сущность в ДТО
     *
     * @param order сущность
     * @return ДТО с данными
     */
    private static OrderDto buildDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .cost(order.getCost())
                .createDateTime(order.getCreateDateTime())
                .customerId(order.getCustomer().getId())
                .description(order.getDescription())
                .endDateTime(order.getEndDateTime())
                .sellerId(order.getSeller().getId())
                .status(order.getStatus().toString())
                .serviceType(order.getServiceType().name())
                .startDateTime(order.getStartDateTime())
                .build();
    }
}
