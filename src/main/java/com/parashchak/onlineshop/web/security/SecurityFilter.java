package com.parashchak.onlineshop.web.security;

import com.parashchak.onlineshop.security.SecurityService;
import com.parashchak.onlineshop.web.util.RequestUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class SecurityFilter implements Filter {
    private final SecurityService securityService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Optional<String> userToken = RequestUtil.getUserToken(httpServletRequest);
        if (!securityService.validateUserToken(userToken)) {
            httpServletResponse.sendRedirect("/login");
            return;
        }
        chain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {
    }
}
