package com.diploma.zoo_hotel.exceptions;

/**
 * Ошибка "Пользователь уже существует"
 */
public class UserExistsException extends RuntimeException {

    public UserExistsException() {
        super();
    }

    public UserExistsException(String message) {
        super(message);
    }
}
