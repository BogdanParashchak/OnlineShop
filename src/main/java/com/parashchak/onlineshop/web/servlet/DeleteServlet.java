package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.service.ProductService;
import com.parashchak.onlineshop.security.SecurityService;
import com.parashchak.onlineshop.web.util.RequestUtil;
import jakarta.servlet.http.*;
import lombok.*;

import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
public class DeleteServlet extends HttpServlet {

    private final ProductService productService;
    private final SecurityService securityService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Optional<String> userToken = RequestUtil.getUserToken(request);
        if (!securityService.validateUserToken(userToken)) {
            response.sendRedirect("/login");
        }else {
            int id = Integer.parseInt(request.getParameter("id"));
            productService.delete(id);
            response.sendRedirect("/products");
        }
    }
}