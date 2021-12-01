package com.demo.chat.application.dto.response;

import com.demo.chat.application.AppHttpStatus;
import lombok.Data;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class Error implements Serializable {

    private String message;

    private Integer code = HttpStatus.BAD_REQUEST.value();

    public static Error of(String message, HttpStatus code) {
        return new Error(message, code.value());
    }

    public static Error of(String message, AppHttpStatus code) {
        return new Error(message, code.value());
    }

    public static Error of(String message) {
        return new Error(message);
    }

    private Error(@NonNull String message, @NonNull Integer code) {
        this.message = message;
        this.code = code;
    }

    public Error(String message) {
        this.message = message;
    }
}
