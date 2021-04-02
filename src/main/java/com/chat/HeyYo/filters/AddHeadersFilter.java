package com.chat.HeyYo.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AddHeadersFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (httpServletResponse.getHeader("Access-Control-Allow-Origin") == null) {

            httpServletResponse.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        }

        if (httpServletResponse.getHeader("Access-Control-Allow-Credentials") == null) {

            httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
