package com.diploma.zoo_hotel.repository;

import com.diploma.zoo_hotel.entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Интерфейс для работы с таблицей refresh-токенов в БД
 */
@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(UUID token);
}
