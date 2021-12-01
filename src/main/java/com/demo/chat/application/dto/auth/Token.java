package com.demo.chat.application.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Token implements Serializable {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("payload")
    private Payload payload;

    public static Token of(@NonNull String accessToken, @NonNull String refreshToken, Payload payload) {
        return new Token(accessToken, refreshToken, payload);
    }

    public static Token of(@NonNull String accessToken, Payload payload) {
        return new Token(accessToken, payload);
    }

    public static Token of(@NonNull String accessToken, @NonNull String refreshToken) {
        return new Token(accessToken, refreshToken);
    }

    private Token(String accessToken, String refreshToken, Payload payload) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.payload = payload;
    }

    private Token(@NonNull String accessToken, Payload payload) {
        this.accessToken = accessToken;
        this.payload = payload;
    }

    private Token(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
