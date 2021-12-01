package com.demo.chat.application.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;

import java.io.Serializable;

@Getter
public class Payload implements Serializable {

    private static final long serialVersionUID = 1;

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private String id;

    private Payload(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Payload of(@NonNull String id, @NonNull String name) {
        return new Payload(id, name);
    }
}
