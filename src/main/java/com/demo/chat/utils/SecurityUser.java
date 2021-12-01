package com.demo.chat.utils;

import com.demo.chat.domain.entity.Client;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.List;


//Внутренний пользователь-объект для секурных операций
@Getter
@Setter
public class SecurityUser extends User implements Serializable {

    private static final long serialVersionUID = 1;

    private final String id;

    public SecurityUser(String id, String username) {
        super(username, "password", List.of(new SimpleGrantedAuthority("CLIENT")));
        this.id = id;
    }

    public SecurityUser(Client client) {
        super(client.getName(), client.getPassword(), List.of(new SimpleGrantedAuthority("CLIENT")));
        this.id = client.getId();
    }

}
