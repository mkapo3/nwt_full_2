package com.nwt.nwt_projekat_user.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.nwt.nwt_projekat_user.config.SecurityConfig.openApiEndpoints;

public class JWTVerifierFilter extends OncePerRequestFilter {

    final JwtTokenUtil jwtTokenUtil;

    public JWTVerifierFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(Stream.of(openApiEndpoints).map(endpoint -> endpoint.replace("**","")).anyMatch(uri -> uri.contains(httpServletRequest.getRequestURI()) || httpServletRequest.getRequestURI().contains(uri) )){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if(!bearerToken.startsWith("Bearer")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String authToken = bearerToken.replace("Bearer", "");

        Claims authClaims = jwtTokenUtil.getAllClaimsFromToken(authToken);
        String username = jwtTokenUtil.getUsernameFromToken(authToken);

        List<Map<String, String>> authorities = (List<Map<String, String>>) authClaims.get("authorities");
        List<GrantedAuthority> grantedAuthorities = authorities.stream().map(map -> new SimpleGrantedAuthority(map.get("authority")))
                .collect(Collectors.toList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
