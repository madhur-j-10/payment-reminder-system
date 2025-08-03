package com.system.payment_reminder_system.filters;

import com.system.payment_reminder_system.utility.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final List<String> WHITE_LIST = List.of(
            "/favicon.ico",
            "/auth"

    );

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();


        // exclude WHITE_LIST_URIS from checking token
        if(WHITE_LIST.stream().anyMatch(path::startsWith)){
            filterChain.doFilter(request, response);
            return;
        }

        String token = null;
        // extract token from all cookies in browser by iterating to each cookie
        if(request.getCookies() != null){
            for(Cookie cookie : request.getCookies()){
                if("jwt".equals(cookie.getName())){
                    token = cookie.getValue();
                    break;
                }
            }
        }
        // if token == null --> Unauthorized and if token == expired --> session expired
        try {
            if(token != null && jwtUtil.validateToken(token)) {

                String email = jwtUtil.extractEmail(token);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth);

            }

        }
        catch (ExpiredJwtException e){

            request.setAttribute("expired", true);
        }
        catch (JwtException e) {

            request.setAttribute("invalid", true);

        }
        filterChain.doFilter(request, response);


    }
}
