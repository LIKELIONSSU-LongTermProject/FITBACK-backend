package com.fitback.ssu.exception;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {
    String getErrorCode();
    String getMessage();
    HttpStatus getHttpStatus();
}
