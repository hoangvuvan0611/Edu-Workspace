package com.vvh.apigateway.util;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET;

    @PostConstruct
    public void init(){
        SECRET = Base64.getEncoder().encodeToString(SECRET.getBytes());
    }

    public void validateToken(final String token){
        Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token);
    }
}
