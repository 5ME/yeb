package com.ygq.server.config;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtUtil
 *
 * @author Yin Guiqing
 * @version 1.0
 * @date 2022-02-17 19:38
 */
@Component
public class JwtUtil {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据用户信息生成 JWT token
     *
     * @param userDetails org.springframework.security.core.userdetails.UserDetails
     * @return 生成的JWT字符串
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 根据负载生成 JWT token
     *
     * @param claims JWT负载
     * @return JWT token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        String username = null;
        try {
            username = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return !isExpiredToken(token) && username.equals(userDetails.getUsername());
    }

    public boolean isExpiredToken(String token) {
        try {
            Date date = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return date.before(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean canRefresh(String token) {
        return !isExpiredToken(token);
    }

    public String refreshToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }
}
