package com.chat.HeyYo.security;

import com.chat.HeyYo.service.UserDetailsServiceImpl;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SignatureException;

import static com.chat.HeyYo.security.SecurityConstants.*;

public class JwtTokenVerifier extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        var token = parseJwt(httpServletRequest);

        try {

            if (token != null && jwtUtils.validateJwtToken(token)) {

                var username = jwtUtils.getUserNameFromJwtToken(token);

                var userDetails = userDetailsService.loadUserByUsername(username);
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (JwtException | SignatureException e) {

            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String parseJwt(HttpServletRequest request) {

        var headerAuth = request.getHeader(HEADER_STRING);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {

            return headerAuth.replace(TOKEN_PREFIX, "");
        }

        var cookie = WebUtils.getCookie(request, HEADER_STRING);

        if (cookie != null) {

            return cookie.getValue();
        }

        return null;
    }
}
