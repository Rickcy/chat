package com.demo.chat.application.dto.response;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 1;

    private T data;
    private Error error;

    public static Response<String> success() {
        return new Response<>();
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(data);
    }

    public static Response failure(Error error) {
        return new Response(error);
    }

    public static <T> Response<T> of(T data, Error error) {
        return new Response<>(error);
    }

    private Response(T data) {
        this.data = data;
    }

    private Response() {
    }

    private Response(Error error) {
        this.error = error;
    }

    public Response(T data, Error error) {
        this.data = data;
        this.error = error;
    }
}
