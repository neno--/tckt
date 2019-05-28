package com.github.nenomm.tckts.rest;

import com.github.nenomm.tckt.lib.InvalidTicketRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        LOG.warn(error, e);

        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, e));
    }

    @ExceptionHandler(value = {InvalidTicketRequestException.class})
    protected ResponseEntity<Object> handleInvalidTicketRequestException(InvalidTicketRequestException e) {
        String error = "Invalid ClientId";
        LOG.warn(error, e);

        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, e));
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleException(Exception e) {
        String error = "Internal Server Error";
        LOG.error(error, e);

        return buildResponseEntity(new ApiError(HttpStatus.HTTP_VERSION_NOT_SUPPORTED.INTERNAL_SERVER_ERROR, error, e));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
