package com.tecno_comfenalco.pa.security.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tecno_comfenalco.pa.security.CustomUserDetailsService;
import com.tecno_comfenalco.pa.shared.utils.jwt.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtCustomFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;
        String username = null;

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // 💡 Lógica para decodificar y validar el token
        if (token != null) {
            try {
                username = jwtUtils.decode(token);
            } catch (Exception e) {
                System.out.println("Invalid JWT from cookie: " + e.getMessage());
                // Si el token es inválido, limpiar la cookie inmediatamente
                Cookie invalidCookie = new Cookie("jwt", "");
                invalidCookie.setHttpOnly(true);
                invalidCookie.setSecure(false);
                invalidCookie.setPath("/");
                invalidCookie.setMaxAge(0);
                invalidCookie.setAttribute("SameSite", "None");
                response.addCookie(invalidCookie);
                token = null; // Marcar token como nulo para no procesarlo más
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtils.validate(token)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                // Usuario no encontrado o token inválido - limpiar la cookie
                System.out.println("Error loading user from JWT: " + e.getMessage());
                Cookie invalidCookie = new Cookie("jwt", "");
                invalidCookie.setHttpOnly(true);
                invalidCookie.setSecure(false);
                invalidCookie.setPath("/");
                invalidCookie.setMaxAge(0);
                invalidCookie.setAttribute("SameSite", "None");
                response.addCookie(invalidCookie);
                // No establecer autenticación - continuar como usuario anónimo
            }
        }

        filterChain.doFilter(request, response);
    }

}
