package com.diploma.zoo_hotel.repository;

import com.diploma.zoo_hotel.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Интерфейс для работы с таблицей пользователей в БД
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
}
