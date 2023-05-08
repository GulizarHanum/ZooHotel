package com.diploma.zoo_hotel.service;

import com.diploma.zoo_hotel.entities.RefreshToken;
import com.diploma.zoo_hotel.entities.User;
import com.diploma.zoo_hotel.exceptions.BadRefreshTokenException;
import com.diploma.zoo_hotel.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

/**
 * Сервис для работы с refresh-токенами
 */
@Service
@Transactional
public class RefreshTokenService {

    @Value("${jwt.refresh-token.expirationMs}")
    private long refreshTokenExpirationMs;

    private final UserService userService;
    private final RefreshTokenRepository repository;

    public RefreshTokenService(RefreshTokenRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    /**
     * Найти сущность Refresh-токена по его содержимому
     *
     * @param refreshToken содержимое
     *
     * @return сущность токена
     */
    public RefreshToken findByUUIDToken(UUID refreshToken) {
        if (refreshToken == null) {
            throw new IllegalArgumentException("Неверное содержимое рефреш-токена");
        }

        return repository
                .findByToken(refreshToken)
                .orElseThrow(() -> new BadRefreshTokenException(String.format("Refresh-токен %s не найден", refreshToken)));
    }

    /**
     * Создать Refresh-токен
     *
     * @param email эл. почта пользователя
     *
     * @return refresh-токен
     */
    public RefreshToken createToken(String email) {
        User user = userService.findUserByEmail(email);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpirationMs));
        refreshToken.setToken(UUID.randomUUID());
        refreshToken = repository.save(refreshToken);
        return refreshToken;
    }

    /**
     * Удалить рефреш-токен по его идентификатору
     *
     * @param tokenId айди токена
     */
    public void deleteToken(Long tokenId) {
        if (tokenId == null) {
            throw new IllegalArgumentException("Неверный идентификатор рефреш-токена");
        }

        repository.deleteById(tokenId);
    }

    /**
     * Проверка на актуальность refresh-токена
     *
     * @param token токен
     */
    public boolean isTokenActual(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            repository.deleteById(token.getId());
            return false;
        } else {
            return true;
        }
    }
}
