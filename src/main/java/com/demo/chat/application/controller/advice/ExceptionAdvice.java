package com.demo.chat.application.controller.advice;

import com.demo.chat.application.dto.response.Error;
import com.demo.chat.application.dto.response.Response;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ResponseEntity<Response> jwtExceptionHandler(JwtException e) {
        log.warn(e.getMessage());
        return new ResponseEntity<>(Response.failure(Error.of(e.getMessage(), HttpStatus.FORBIDDEN)), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ResponseEntity<Response> badCredentialsExceptionHandler(BadCredentialsException e) {
        log.warn(e.getMessage());
        return new ResponseEntity<>(Response.failure(Error.of("Неправильный логин или пароль", HttpStatus.FORBIDDEN)), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<Response> usernameNotFoundExceptionHandler(UsernameNotFoundException e) {
        log.warn(e.getMessage());
        return new ResponseEntity<>(Response.failure(Error.of(e.getMessage(), HttpStatus.NOT_FOUND)), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<Response> runtimeExceptionHandler(RuntimeException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(Response.failure(Error.of(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
