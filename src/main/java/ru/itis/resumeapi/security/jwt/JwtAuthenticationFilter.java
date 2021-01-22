package ru.itis.resumeapi.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component( "jwtAuthenticationFilter")
public class JwtAuthenticationFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String token = null;
        token = request.getHeader("Authorization");
        if (token != null){
            SecurityContextHolder.getContext().setAuthentication(createAuthentication(token));
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    protected Authentication createAuthentication(String token) {
        Authentication authentication = new JwtAuthentication(token);
        return authentication;
    }
}
