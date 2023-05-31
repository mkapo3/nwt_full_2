package com.spingdatajpa.springboot.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.spingdatajpa.springboot.config.SecurityConfig.openApiEndpoints;

public class JwtVerifierFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        if(!Stream.of(openApiEndpoints).map(endpoint -> endpoint.replace("**","")).anyMatch(endpoint -> httpServletRequest.getRequestURI().contains(endpoint))) {
            String username = httpServletRequest.getHeader("username");
            List<Map<String, String>> authorities = new ArrayList<>();
            String authoritiesStr = httpServletRequest.getHeader("authorities");
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();

            simpleGrantedAuthorities = Arrays.stream(authoritiesStr.split(",")).skip(1).distinct()
                    .map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
