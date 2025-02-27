package com.example.top_up_store_backend.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.example.top_up_store_backend.utils.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
  private final HandlerExceptionResolver handlerExceptionResolver;
  private final JWTService jwtService;
  private final UserDetailsService userDetailsService;

  

  public JWTAuthenticationFilter(HandlerExceptionResolver handlerExceptionResolver, JWTService jwtService,
      UserDetailsService userDetailsService) {
    this.handlerExceptionResolver = handlerExceptionResolver;
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
  }


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
          filterChain.doFilter(request, response);
          return;
        }
        try {
          final String jwt = authHeader.substring(7);
          final String username = jwtService.extractUsername(jwt);
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          if (username != null && authentication == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, userDetails)) {
              UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
              authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(authToken);
            }
          }
          filterChain.doFilter(request, response);
        } catch (Exception e) {
          handlerExceptionResolver.resolveException(request, response, null, e);
        }
  }
}
