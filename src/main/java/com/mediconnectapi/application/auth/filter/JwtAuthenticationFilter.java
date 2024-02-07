package com.mediconnectapi.application.auth.filter;

import com.mediconnectapi.application.auth.controller.service.AuthUserService;
import com.mediconnectapi.application.auth.controller.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  private final AuthUserService authUserService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain) throws ServletException, IOException {
    final String authorizationHeader = request.getHeader("Authorization");
    if (StringUtils.isEmpty(authorizationHeader) || !StringUtils.startsWith(authorizationHeader, "Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      final String jwt = authorizationHeader.substring(7);
      final String userEmail = jwtService.extractUserName(jwt);

      if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
        final UserDetails userDetails = authUserService.userDetailsService().loadUserByUsername(userEmail);

        if (jwtService.isTokenValid(jwt, userDetails)) {
          final SecurityContext context = SecurityContextHolder.createEmptyContext();

          final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          context.setAuthentication(authToken);
          SecurityContextHolder.setContext(context);
        }
      }
    } catch (ExpiredJwtException e) {
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("{\"error\":\"Token expired\"}");
      response.getWriter().flush();
      response.getWriter().close();
      return;
    }

    filterChain.doFilter(request, response);
  }
}
