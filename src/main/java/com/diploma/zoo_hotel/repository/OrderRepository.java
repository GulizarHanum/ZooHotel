package com.diploma.zoo_hotel.repository;

import com.diploma.zoo_hotel.entities.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Интерфейс для работы с таблицей заказов в БД
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query(value = "select * from order_report where seller_id = :sellerId", nativeQuery = true)
    List<Order> findBySeller(@Param(value = "sellerId") Long id);

    @Query(value = "select * from order_report where customer_id = :customerId", nativeQuery = true)
    List<Order> findByCustomer(@Param(value = "customerId") Long id);
}
