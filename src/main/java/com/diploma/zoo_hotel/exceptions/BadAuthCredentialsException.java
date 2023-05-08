package com.diploma.zoo_hotel.exceptions;

/**
 * Исключение "Неверные данные для аутентификации"
 */
public class BadAuthCredentialsException extends RuntimeException {

    public BadAuthCredentialsException() {
        super();
    }

    public BadAuthCredentialsException(String message) {
        super(message);
    }
}
