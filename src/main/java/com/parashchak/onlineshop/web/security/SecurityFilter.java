package com.parashchak.onlineshop.web.security;

import com.parashchak.onlineshop.security.SecurityService;
import com.parashchak.onlineshop.web.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@Slf4j
public class SecurityFilter implements Filter {

    private final static List<String> ALLOWED_URIS = List.of("/login", "/products", "/", "/products/search");
    private SecurityService securityService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        ApplicationContext appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
        securityService = appContext.getBean("securityService", SecurityService.class);
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String uri = httpServletRequest.getRequestURI();
        if (ALLOWED_URIS.contains(uri)) {
            log.info("No authorization needed");
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