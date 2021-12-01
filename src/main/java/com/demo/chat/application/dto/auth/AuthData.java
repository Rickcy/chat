package com.demo.chat.application.dto.auth;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AuthData implements Serializable {

    private static final long serialVersionUID = 1;

    @NonNull
    private String name;

    @NonNull
    private char[] password;

}
