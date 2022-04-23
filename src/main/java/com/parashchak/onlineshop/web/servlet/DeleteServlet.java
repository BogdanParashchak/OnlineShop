package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.service.ProductService;
import jakarta.servlet.http.*;
import lombok.*;

import java.util.List;

import static com.parashchak.onlineshop.web.validator.CookieValidator.validateCookie;

@AllArgsConstructor
@RequiredArgsConstructor
public class DeleteServlet extends HttpServlet {

    private final ProductService productService;
    private List<String> sessionList;

    @Override
    @SneakyThrows
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        validateCookie(request, response, sessionList);
        int id = Integer.parseInt(request.getParameter("id"));
        productService.delete(id);
        response.sendRedirect("/products");
    }
}