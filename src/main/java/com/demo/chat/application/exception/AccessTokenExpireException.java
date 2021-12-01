package com.demo.chat.application.exception;


public class AccessTokenExpireException extends TokenExpireException {
    public AccessTokenExpireException(String message) {
        super(message);
    }

    public AccessTokenExpireException() {
        super("Время жизни токена доступа истекло");
    }
}
