package com.project.skill_share.configuration;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
	
	private final Key key;

//	private final String secret = "mysecretkey123456789012345678901234"; // at least 32 chars
//	private final Key key = Keys.hmacShaKeyFor(secret.getBytes());

	 public JwtUtil(@Value("${jwt.secret}") String secret) {
//		   System.out.println("JWT Secret: " + secret);
	        this.key = Keys.hmacShaKeyFor(secret.getBytes());
	    }
	 
    // Token generate garne
    public String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    // Username extract garne
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token).getBody().getSubject();
    }

    // Token expire bha ki check garne
    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getExpiration();
        return expiration.before(new Date());
    }

    // Token valid cha ki check garne
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
