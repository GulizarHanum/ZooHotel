package com.diploma.zoo_hotel.exceptions;

/**
 * Исключение при ошибке конвертации фото
 */
public class ConvertPhotoException extends RuntimeException {
    public ConvertPhotoException() {
        super();
    }

    public ConvertPhotoException(String message) {
        super(message);
    }

    public ConvertPhotoException(String message, Throwable cause) {
        super(message, cause);
    }
}
