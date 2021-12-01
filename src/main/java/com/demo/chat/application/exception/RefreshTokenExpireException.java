package com.demo.chat.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class RefreshTokenExpireException extends RuntimeException {
    public RefreshTokenExpireException(String message) {
        super(message);
    }
    public RefreshTokenExpireException() {
        super("Время жизни токена обновления истекло");
    }
}
