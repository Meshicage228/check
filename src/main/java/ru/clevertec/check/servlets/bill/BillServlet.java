package ru.clevertec.check.servlets.bill;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.check.dto.UserDto;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.exceptions.NotEnoughMoneyException;
import ru.clevertec.check.exceptions.ResourceNotFoundException;
import ru.clevertec.check.factory.ServiceFactory;
import ru.clevertec.check.service.ClientService;
import ru.clevertec.check.utils.InputDataValidator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.stream.Collectors;

import static javax.servlet.http.HttpServletResponse.*;

@WebServlet(name = "Bill servlet", urlPatterns = "/check")
public class BillServlet extends HttpServlet {
    private ObjectMapper objectMapper;
    private ClientService clientService;
    private InputDataValidator validator;

    @Override
    public void init() {
        objectMapper = new ObjectMapper();
        validator = new InputDataValidator();
        clientService = ServiceFactory.createClientService();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String jsonString = req.getReader().lines().collect(Collectors.joining());
        UserDto request = objectMapper.readValue(jsonString, UserDto.class);
        File file = null;
        try {
            validator.validateUserDto(request);
            UserDto userDto = clientService.formClient(request);
            file = clientService.formTotalBill(userDto);

            assert file != null;
            try(InputStream in = new FileInputStream(file);
                OutputStream out = resp.getOutputStream()) {

                byte[] buffer = new byte[1048];
                resp.setContentType("text/csv");
                resp.setHeader("Content-disposition", "attachment; filename=result.csv");
                resp.setStatus(SC_OK);

                int numBytesRead;
                while ((numBytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, numBytesRead);
                }
            }

        } catch (BadRequestException | NotEnoughMoneyException | ResourceNotFoundException e) {
            resp.setStatus(e.getStatusCode());
        } catch (Exception e){
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
