package com.jalbertolrz.auth_service.service;

import com.jalbertolrz.auth_service.model.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    // Genera token normal con roles
    public String generateToken(String username, List<Role> roles) {
        return buildToken(username, roles, jwtExpiration);
    }

    // Genera refresh token sin roles
    public String generateRefreshToken(String username) {
        return buildToken(username, Collections.emptyList(), refreshExpiration);
    }

    private String buildToken(String username, List<Role> roles, Long expiration) {
        Map<String, Object> claims = new HashMap<>();
        if (!roles.isEmpty()) {
            // Convertimos roles a strings
            claims.put("roles", roles.stream().map(Enum::name).collect(Collectors.toList()));
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public List<Role> extractRoles(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<String> roles = claims.get("roles", List.class);
        if (roles == null) return Collections.emptyList();

        return roles.stream().map(Role::valueOf).collect(Collectors.toList());
    }
}