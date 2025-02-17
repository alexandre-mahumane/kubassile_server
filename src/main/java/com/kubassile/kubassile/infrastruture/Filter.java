package com.kubassile.kubassile.infrastruture;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kubassile.kubassile.exceptions.ForbiddenException;
import com.kubassile.kubassile.repository.UsersRepository;
import com.kubassile.kubassile.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Filter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UsersRepository usersRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null) {
            try {
                String subject = jwtService.decodeToken(token.replace("Bearer ", ""));
                var user = usersRepository.findByName(subject);

                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ForbiddenException e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write("""
                            {
                                "status": 403,
                                "timestamp": "%s",
                                "message": "%s",
                                "error": "Forbidden"
                            }
                        """.formatted(java.time.LocalDateTime.now(), e.getMessage()));
                return;
            }
        }

        filterChain.doFilter(request, response);

    }

}
