package com.crit.oauthjwt2.common.advice;

import com.crit.oauthjwt2.common.exception.BadRequestException;
import com.crit.oauthjwt2.common.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdviceController {
    // 비즈니스 오류
    @ExceptionHandler(value = BadRequestException.class)
    protected ResponseEntity<ErrorResponse> unAuthenticatedException(BadRequestException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
    }
}
