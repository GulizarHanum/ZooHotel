package com.diploma.zoo_hotel.controller;

import com.diploma.zoo_hotel.dto.OrderDto;
import com.diploma.zoo_hotel.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Заказ", description = "API для работы с заказами")
public class OrderController {

    private final OrderService orderService;

    @GetMapping(path = "/public/orders/employee/{employeeId}")
    @Operation(description = "Получить заказы по id работник")
    public List<OrderDto> getOrdersByEmployee(@Parameter(description = "Идентификатор заказа") @PathVariable Long employeeId) {
        return orderService.getOrdersByEmployee(employeeId);
    }

    @GetMapping(path = "/public/orders/customer/{customerId}")
    @Operation(description = "Получить заказы по id клиента")
    public List<OrderDto> getOrdersByCustomer(@Parameter(description = "Идентификатор заказа") @PathVariable Long customerId) {
        return orderService.getOrdersByCustomer(customerId);
    }

    @PutMapping(path = "/public/orders")
    @Operation(description = "Получить среднюю оценку отзывов по айди профиля")
    public OrderDto updateOrder(@Parameter(description = "Данные заказа") @RequestBody @Valid OrderDto dto) {
        return orderService.update(dto);
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создать заказ")
    public OrderDto createOrder(@Parameter(description = "Данные заказа") @RequestBody @Valid OrderDto dto) {
        return orderService.createOrder(dto);
    }

    @DeleteMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить заказ")
    public void deleteOrder(@Parameter(description = "Идентификатор отзыва") @PathVariable Long id) {
        orderService.delete(id);
    }
}
