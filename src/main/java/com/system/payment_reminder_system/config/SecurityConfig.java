package com.system.payment_reminder_system.config;


import com.system.payment_reminder_system.filters.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    private final String[] WHITE_LIST_URIS = {
            "/favicon.ico",
      "/auth/**","/css/**","/js/**"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {})
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WHITE_LIST_URIS).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {

                    String path = request.getRequestURI();

                    if(!path.startsWith("/error")){
                        String msg = "unauthorized"; // default

                        if(request.getAttribute("expired") != null){
                            msg = "expired";

                        }

                        // Check if it's a fetch/ajax request
                        String requestedWith = request.getHeader("X-Requested-With");

                        if("XMLHttpRequest".equalsIgnoreCase(requestedWith)){
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
                            response.setContentType("application/json");
                            response.getWriter().write("{ \"redirect\": \"/auth/register?msg="  + msg + "\"}");
                        }
                        else{
                            response.sendRedirect("/auth/register?msg=" + msg);

                        }
                    }



                })
                );
        //add our custom filter(one time when program start running)
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }




}
