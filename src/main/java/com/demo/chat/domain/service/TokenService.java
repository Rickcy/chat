package com.demo.chat.domain.service;

import com.demo.chat.application.dto.auth.Payload;
import com.demo.chat.application.dto.auth.Token;
import com.demo.chat.application.exception.InvalidTokenAuthenticationException;
import com.demo.chat.application.exception.AccessTokenExpireException;
import com.demo.chat.application.exception.RefreshTokenExpireException;
import com.demo.chat.utils.SecurityUser;
import io.jsonwebtoken.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final static String ACCESS_TOKEN = "access-token";
    private final static String REFRESH_TOKEN = "refresh-token";

    @Value("${security.token.secret-key:secret-key}")
    private String secretKey = "secret-key";

    @Value("${security.token.access.expire-length:3600000}")
    private final int expireAccessToken = 60 * 1000 * 60; // 1h

    @Value("${security.token.refresh.expire-length:10600000}")
    private final int expireRefreshToken = 60 * 1000 * 60 * 4; // 4h

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    public Token generate(@NonNull SecurityUser user, HttpServletResponse res) {
        String refreshToken = generateRefreshToken(user);
        Cookie cookie = new Cookie(REFRESH_TOKEN, refreshToken);
        cookie.setMaxAge(expireRefreshToken);
        res.addCookie(cookie);
        String accessToken = generateAccessToken(user);
        return Token.of(accessToken, Payload.of(user.getId(), user.getUsername()));
    }


    public Authentication getAuthentication(String token) {
        SecurityUser user = getUser(parseToken(token).getBody());
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    public String resolveAccessToken(HttpServletRequest req) {
        String value = null;
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            value = bearerToken.substring(7);
        }

        return value;
    }

    public String resolveRefreshToken(HttpServletRequest req) {
        return Stream.ofNullable(req.getCookies())
                .filter(cookies -> cookies.length > 0)
                .flatMap(Arrays::stream)
                .filter(cookie -> cookie.getName().equals("refresh-token"))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new InvalidTokenAuthenticationException("Токен обновления не может быть пустым"));

    }

    public boolean validateAccessToken(String accessToken) {
        try {
            return validateToken(accessToken, ACCESS_TOKEN);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new AccessTokenExpireException();
        }
    }

    private boolean validateToken(String token, String tokenName) {
        try {
            Jws<Claims> claims = parseToken(token);
            return claims.getBody().getSubject().equals(tokenName) && claims.getBody().getExpiration().after(new Date());
        } catch (SignatureException | MalformedJwtException exception) {
            throw new InvalidTokenAuthenticationException("Некорректный токен");
        } catch (IllegalArgumentException argumentException) {
            throw new InvalidTokenAuthenticationException("Токен не прошел проверку");
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            return validateToken(refreshToken, REFRESH_TOKEN);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new RefreshTokenExpireException();
        }
    }


    public Token renew(String refreshToken, HttpServletResponse response) {
        if (validateRefreshToken(refreshToken)) {
            SecurityUser user = getUser(parseToken(refreshToken).getBody());
            return generate(user, response);
        }
        throw new InvalidTokenAuthenticationException("Некорректный токен");
    }

    private Jws<Claims> parseToken(String accessToken) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken);
    }

    private SecurityUser getUser(Claims claims) {
        return new SecurityUser(claims.get("client_id", String.class), claims.get("name", String.class));
    }


    private String generateAccessToken(SecurityUser user) {
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setClaims(loadData(user).setSubject(ACCESS_TOKEN))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expireAccessToken))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    private String generateRefreshToken(SecurityUser user) {

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setClaims(loadData(user).setSubject(REFRESH_TOKEN))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expireRefreshToken))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    private Claims loadData(SecurityUser user) {
        Claims claims = Jwts.claims();
        claims.put("client_id", user.getId());
        claims.put("name", user.getUsername());
        return claims;
    }

}
