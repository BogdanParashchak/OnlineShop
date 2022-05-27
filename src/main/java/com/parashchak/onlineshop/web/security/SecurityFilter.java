package com.parashchak.onlineshop.web.security;

import com.parashchak.onlineshop.security.SecurityService;
import com.parashchak.onlineshop.service.ServiceLocator;
import com.parashchak.onlineshop.web.util.RequestUtil;
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class SecurityFilter implements Filter {
    private final SecurityService securityService = ServiceLocator.getService(SecurityService.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (((HttpServletRequest) request).getServletPath().equals("/login")) {
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        log.info("Check for authorization");
        Optional<String> userToken = RequestUtil.getUserToken(httpServletRequest);
        if (!securityService.validateUserToken(userToken)) {
            httpServletResponse.sendRedirect("/login");
            log.info("Unauthorized");
            return;
        }

        log.info("Authorized");
        chain.doFilter(httpServletRequest, httpServletResponse);
    }
}