package ru.clevertec.check.servlets.discountCard;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.check.dto.CardDto;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.exceptions.ResourceNotFoundException;
import ru.clevertec.check.factory.ServiceFactory;
import ru.clevertec.check.service.CardService;
import ru.clevertec.check.utils.InputDataValidator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import static javax.servlet.http.HttpServletResponse.*;

@WebServlet(name = "DiscountCard servlet", urlPatterns = "/discountcards")
public class DiscountCardServlet extends HttpServlet {
    private ObjectMapper objectMapper;
    private CardService cardService;
    private InputDataValidator validator;

    @Override
    public void init() {
        objectMapper = new ObjectMapper();
        validator = new InputDataValidator();
        cardService = ServiceFactory.createCardService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.valueOf(req.getParameter("id"));
        CardDto byId = null;

        try {
            byId = cardService.getById(id);
            resp.setStatus(SC_OK);
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(byId));
        } catch (ResourceNotFoundException e) {
            resp.setStatus(e.getStatusCode());
        } catch (Exception e) {
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String jsonString = req.getReader().lines().collect(Collectors.joining());
        CardDto card = objectMapper.readValue(jsonString, CardDto.class);
        try {
            validator.validateDiscountCard(card);
            cardService.save(card);
            resp.setStatus(SC_CREATED);
        } catch (BadRequestException e) {
            resp.setStatus(e.getStatusCode());
        } catch (Exception e) {
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.valueOf(req.getParameter("id"));
        try {
            CardDto byId = cardService.getById(id);
            String jsonString = req.getReader().lines().collect(Collectors.joining());
            CardDto card = objectMapper.readValue(jsonString, CardDto.class);
            validator.validateDiscountCard(card);
            cardService.fullUpdateCard(card, id);
            resp.setStatus(SC_CREATED);
        } catch (ResourceNotFoundException e) {
            resp.setStatus(e.getStatusCode());
        } catch (Exception e) {
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.valueOf(req.getParameter("id"));
        try {
            CardDto byId = cardService.getById(id);
            cardService.deleteById(id);
            resp.setStatus(SC_ACCEPTED);
        } catch (ResourceNotFoundException e) {
            resp.setStatus(e.getStatusCode());
        } catch (Exception e) {
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
