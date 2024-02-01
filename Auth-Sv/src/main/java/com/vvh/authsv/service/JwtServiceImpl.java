package com.vvh.authsv.service;

import com.vvh.authsv.constant.TypeToken;
import com.vvh.authsv.entity.User;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService{

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiresTime}")
    private String expiresTime;

    @PostConstruct
    public void init(){
        SECRET = Base64.getEncoder().encodeToString(SECRET.getBytes());
    }

    @Override
    public String generateToken(Authentication authentication, TypeToken tokenType) {
        User user = (User) authentication.getPrincipal();

        long exTime = TypeToken.ACCESS.equals(tokenType)
                ? Long.parseLong(expiresTime)
                : Long.parseLong(expiresTime)  * 5;

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + exTime))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    @Override
    public boolean validateToken(String aToken) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(aToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        throw new RuntimeException("Jwt token invalid");
    }
}
