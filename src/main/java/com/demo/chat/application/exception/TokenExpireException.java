package com.demo.chat.application.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TokenExpireException extends JwtException {
    public TokenExpireException(String message) {
        super(message);
    }
}
