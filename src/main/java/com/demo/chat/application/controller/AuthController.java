package com.demo.chat.application.controller;

import com.demo.chat.application.dto.auth.AuthData;
import com.demo.chat.application.dto.auth.Token;
import com.demo.chat.application.dto.response.Response;
import com.demo.chat.domain.service.SecurityService;
import com.demo.chat.domain.service.TokenService;
import com.demo.chat.utils.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SecurityService securityService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public Response<Token> login(@RequestBody AuthData authData, HttpServletResponse res) {
        SecurityUser securityUser = securityService.login(authData);
        return Response.success(tokenService.generate(securityUser, res));
    }

    //Для обновоения токенов по токену обновления
    @GetMapping("/renew")
    public Response<Token> renewToken(HttpServletRequest req, HttpServletResponse res) {
        String refreshToken = tokenService.resolveRefreshToken(req);
        return Response.success(tokenService.renew(refreshToken, res));
    }

}
