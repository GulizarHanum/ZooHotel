package com.diploma.zoo_hotel.advice;

import com.diploma.zoo_hotel.exceptions.BadAuthCredentialsException;
import com.diploma.zoo_hotel.exceptions.BadRefreshTokenException;
import com.diploma.zoo_hotel.exceptions.ConvertPhotoException;
import com.diploma.zoo_hotel.exceptions.UserExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.util.Map;

/**
 * Кастомный обработчик ошибок системы
 */
@ControllerAdvice
public class ErrorHandlingControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    ResponseEntity<Object> onValidationExceptionError(ValidationException e, WebRequest request) {
        return handleExceptionInternal(e, Map.of("error", e.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    ResponseEntity<Object> onEntityNotFountException(EntityNotFoundException e, WebRequest request) {
        return handleExceptionInternal(e, Map.of("error", e.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    ResponseEntity<Object> onIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
        return handleExceptionInternal(e, Map.of("error", e.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ConvertPhotoException.class)
    @ResponseBody
    ResponseEntity<Object> onConvertPhotoException(ConvertPhotoException e, WebRequest request) {
        return handleExceptionInternal(e, Map.of("error", e.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(BadAuthCredentialsException.class)
    @ResponseBody
    ResponseEntity<Object> onBadAuthCredentialException(BadAuthCredentialsException e, WebRequest request) {
        return handleExceptionInternal(e, Map.of("error", e.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(UserExistsException.class)
    @ResponseBody
    ResponseEntity<Object> onUserExistsException(UserExistsException e, WebRequest request) {
        return handleExceptionInternal(e, Map.of("error", e.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(BadRefreshTokenException.class)
    @ResponseBody
    ResponseEntity<Object> onBadRefreshTokenException(BadRefreshTokenException e, WebRequest request) {
        return handleExceptionInternal(e, Map.of("error", e.getMessage()), new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }
}
