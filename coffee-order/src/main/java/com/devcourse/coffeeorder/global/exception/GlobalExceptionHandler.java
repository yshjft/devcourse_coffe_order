package com.devcourse.coffeeorder.global.exception;

import com.devcourse.coffeeorder.global.exception.customexception.CreationException;
import com.devcourse.coffeeorder.global.exception.customexception.UpdateException;
import com.devcourse.coffeeorder.global.exception.customexception.badrequest.BadRequestException;
import com.devcourse.coffeeorder.global.exception.customexception.notfound.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponseDto> handleBindException(BindException e) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.of(HttpStatus.BAD_REQUEST.value(), e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseDto);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.of(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        logger.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseDto);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorResponseDto> handleBadRequestException(BadRequestException e) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.of(HttpStatus.BAD_REQUEST.value(), e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseDto);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException e) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.of(HttpStatus.NOT_FOUND.value(), e.getMessage());
        logger.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponseDto);
    }

    @ExceptionHandler(CreationException.class)
    public ResponseEntity<ErrorResponseDto> handleCreationException(CreationException e) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        logger.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponseDto);
    }

    @ExceptionHandler(UpdateException.class)
    public ResponseEntity<ErrorResponseDto> handleUpdateException(UpdateException e) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        logger.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponseDto);
    }
}
