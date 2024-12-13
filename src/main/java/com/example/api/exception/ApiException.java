package com.example.api.exception;

import com.example.api.error.ApiError;
import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private final ApiError error;

    public ApiException(ApiError error) {
        super(error.getMessage());
        this.error = error;
    }

    public ApiError getError() {
        return error;
    }

    public HttpStatus getStatus() {
        return error.getStatus();
    }
}