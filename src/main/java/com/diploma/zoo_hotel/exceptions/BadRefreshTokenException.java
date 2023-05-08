package com.diploma.zoo_hotel.exceptions;

/**
 * Исключение "Неверный refresh-токен"
 */
public class BadRefreshTokenException extends RuntimeException {

    public BadRefreshTokenException() {
        super();
    }

    public BadRefreshTokenException(String message) {
        super(message);
    }
}
