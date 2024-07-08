package ru.clevertec.check.servlets.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.exceptions.ResourceNotFoundException;
import ru.clevertec.check.factory.ServiceFactory;
import ru.clevertec.check.service.ProductService;
import ru.clevertec.check.utils.InputDataValidator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import static javax.servlet.http.HttpServletResponse.*;

@WebServlet(name = "Product servlet",
        urlPatterns = "/products")
public class ProductServlet extends HttpServlet {
    private ProductService productService;
    private ObjectMapper objectMapper;
    private InputDataValidator validator;

    @Override
    public void init() {
        objectMapper = new ObjectMapper();
        validator = new InputDataValidator();
        productService = ServiceFactory.createProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.valueOf(req.getParameter("id"));
        ProductDto byId = null;
        try {
            byId = productService.getById(id);
            resp.setStatus(SC_OK);
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(byId));
        } catch (ResourceNotFoundException e) {
            resp.setStatus(SC_NOT_FOUND);
        } catch (Exception e){
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        String jsonString = req.getReader().lines().collect(Collectors.joining());
        ProductDto product = objectMapper.readValue(jsonString, ProductDto.class);
        try {
            validator.validateProductDto(product);
            productService.getById(id);
            productService.fullUpdateProduct(product, id);
            resp.setStatus(SC_CREATED);
        } catch (BadRequestException | ResourceNotFoundException e) {
            resp.setStatus(e.getStatusCode());
        } catch (Exception e) {
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String jsonString = req.getReader().lines().collect(Collectors.joining());
        ProductDto product = objectMapper.readValue(jsonString, ProductDto.class);
        try {
            validator.validateProductDto(product);
            productService.save(product);
            resp.setStatus(SC_CREATED);
        } catch (BadRequestException e){
            resp.setStatus(e.getStatusCode());
        }catch (Exception e){
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.valueOf(req.getParameter("id"));
        try {
            ProductDto byId = productService.getById(id);
            productService.deleteById(id);
            resp.setStatus(SC_ACCEPTED);
        } catch (ResourceNotFoundException e) {
            resp.setStatus(e.getStatusCode());
        } catch (Exception e) {
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
