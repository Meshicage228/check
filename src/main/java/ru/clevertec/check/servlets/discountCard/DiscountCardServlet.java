package ru.clevertec.check.servlets.discountCard;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.check.dto.CardDto;
import ru.clevertec.check.exceptions.ResourceNotFoundException;
import ru.clevertec.check.factory.ServiceFactory;
import ru.clevertec.check.service.CardService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import static javax.servlet.http.HttpServletResponse.*;

@WebServlet(name = "DiscountCard servlet",
        urlPatterns = "/discountcards")
public class DiscountCardServlet extends HttpServlet {
    private ObjectMapper objectMapper;
    private CardService cardService;

    @Override
    public void init() {
        objectMapper = new ObjectMapper();
        cardService = ServiceFactory.createCardService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        CardDto byId = null;

        try {
            byId = cardService.getById(id);
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String jsonString = req.getReader().lines().collect(Collectors.joining());
        CardDto workoutDto = objectMapper.readValue(jsonString, CardDto.class);
        // TODO : Validate value
        try {
            cardService.save(workoutDto);
        } catch (Exception e) {
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
        resp.setStatus(SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        try {
            CardDto byId = cardService.getById(id);
        } catch (ResourceNotFoundException e) {
            resp.setStatus(SC_NOT_FOUND);
        } catch (Exception e){
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
        }

        String jsonString = req.getReader().lines().collect(Collectors.joining());
        CardDto workoutDto = objectMapper.readValue(jsonString, CardDto.class);
        // TODO : Validate value
        cardService.fullUpdateCard(workoutDto, id);
        resp.setStatus(SC_CREATED);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.valueOf(req.getParameter("id"));
        try {
            CardDto byId = cardService.getById(id);
        } catch (ResourceNotFoundException e) {
            resp.setStatus(SC_NOT_FOUND);
        } catch (Exception e){
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
        }

        cardService.deleteById(id);
        resp.setStatus(SC_ACCEPTED);
    }
}
