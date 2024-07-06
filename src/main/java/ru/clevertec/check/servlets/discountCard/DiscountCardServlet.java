package ru.clevertec.check.servlets.discountCard;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.check.factory.ServiceFactory;
import ru.clevertec.check.service.CardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DiscountCard servlet",
        urlPatterns = "/discountcards")
public class DiscountCardServlet extends HttpServlet {
    private ObjectMapper objectMapper;
    private CardService cardService;

    @Override
    public void init() throws ServletException {
        objectMapper = new ObjectMapper();
        cardService = ServiceFactory.createCardService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
