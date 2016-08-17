package org.driem.api;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DuplicateKeyException.class})
    public ResponseEntity<Object> handleConstraintViolation(DuplicateKeyException ex, WebRequest request) {
        String method = ((ServletWebRequest) request).getRequest().getMethod();
        String bodyOfResponse;
        switch (method) {
            case "POST":
                bodyOfResponse = "Trying to add something that is already there";
                break;
            case "DELETE":
                bodyOfResponse = "Trying to remove something that is in use.";
                break;
            default:
                bodyOfResponse = "Integrity exception while doing different than add or delete";
        }
        ErrorResponse response = new ErrorResponse("integrity",bodyOfResponse);
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
