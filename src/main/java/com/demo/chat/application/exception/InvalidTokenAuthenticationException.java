package com.demo.chat.application.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidTokenAuthenticationException extends JwtException {

    public InvalidTokenAuthenticationException(String message) {
        super(message);
    }
}
