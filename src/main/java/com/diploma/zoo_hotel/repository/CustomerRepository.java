package com.diploma.zoo_hotel.repository;

import com.diploma.zoo_hotel.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс для работы с БД
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
