package com.demo.chat.domain.service;

import com.demo.chat.application.dto.auth.AuthData;
import com.demo.chat.utils.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final AuthenticationManager authenticationManager;

    public SecurityUser login(AuthData authData) {
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(authData.getName(), String.valueOf(authData.getPassword()));
        Authentication authenticate = authenticationManager.authenticate(auth);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return (SecurityUser) authenticate.getPrincipal();
    }

}
