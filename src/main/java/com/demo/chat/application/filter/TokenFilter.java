package com.demo.chat.application.filter;

import com.demo.chat.application.dto.response.Response;
import com.demo.chat.application.dto.response.Error;
import com.demo.chat.application.exception.InvalidTokenAuthenticationException;
import com.demo.chat.domain.service.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Обработка каждого запроса на предмет токенов
public class TokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public TokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        String accessToken = tokenService.resolveAccessToken(req);
        try {
            if (accessToken != null && tokenService.validateAccessToken(accessToken)) {
                Authentication auth = tokenService.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (JwtException e) {
            logger.error(e);
            res.setStatus(HttpStatus.FORBIDDEN.value());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.getWriter().write(convertToJson(Response.failure(Error.of(e.getMessage(), HttpStatus.FORBIDDEN))));
            return;
        } catch (Exception exception) {
            logger.error(exception);
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.getWriter().write(convertToJson(Response.failure(Error.of(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR))));
            return;
        }

        filterChain.doFilter(req, res);
    }


    private String convertToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

}