package com.diploma.zoo_hotel.exceptions;

/**
 * Ошибка "Неверный JWT токен"
 */
public class InvalidJwtTokenException extends RuntimeException {

    public InvalidJwtTokenException() {
        super();
    }

    public InvalidJwtTokenException(String message) {
        super(message);
    }
}
