package com.devcourse.coffeeorder.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private int status;
    private String message;
    private T result;

    public ResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseDto(int status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getResult() {
        return result;
    }
}
