package com.project.skill_share.configuration;


import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String token = null;

        // Token header ma "Bearer " prefix sanga aauchha vane matra extract garne
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); 
            System.out.println("JWT Token: " + token);// "Bearer " part hataune
            
            try {
            	
                username = jwtUtil.extractUsername(token);
                System.out.println("Extracted Username: " + username);
            } catch (Exception e) {
                System.out.println("Invalid JWT Token");
                e.printStackTrace();
            }
        }

        // User authentication context set garne (once only)
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.validateToken(token, username)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Filter chain continue garne
        filterChain.doFilter(request, response);
    }
}
