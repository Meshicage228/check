package ru.clevertec.check.servlets.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.factory.ServiceFactory;
import ru.clevertec.check.service.ProductService;
import ru.clevertec.check.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Product servlet",
        urlPatterns = "/products")
public class ProductServlet extends HttpServlet {
    private ProductService productService;
    private ObjectMapper objectMapper;

    @Override
    public void init() {
        objectMapper = new ObjectMapper();
        productService = ServiceFactory.createProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        ProductDto byId = productService.getById(id);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().write(objectMapper.writeValueAsString(byId));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
