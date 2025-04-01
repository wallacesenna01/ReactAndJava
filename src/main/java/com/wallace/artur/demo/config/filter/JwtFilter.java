package com.wallace.artur.demo.config.filter;

import com.wallace.artur.demo.application.images.jwt.InvalidTokenException;
import com.wallace.artur.demo.application.images.jwt.JwtServices;
import com.wallace.artur.demo.domain.entity.User;
import com.wallace.artur.demo.domain.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtServices jwtServices;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getToken(request);
        if (token != null) {

            try {
                String email = jwtServices.getEmailFromToken(token);
                User user = userService.getByEmail(email);
                setUserAsAuthenticated(user);
            }catch (InvalidTokenException e) {
                log.error("Token inválido {} ", e.getMessage());
            }catch (Exception e) {
                log.error("Error na validacão do token {} ", e.getMessage());
            }
        }

        filterChain.doFilter(request,response);
    }

    private void setUserAsAuthenticated(User user) {
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private String getToken(HttpServletRequest request) {
       String authHeader =  request.getHeader("Authorization");
         if(authHeader != null) {
          String [] authHeaderParts  =  authHeader.split(" ");
          if(authHeader.length() == 2) {
             return authHeaderParts[1];
          }
         }
         return null;
     }
}
