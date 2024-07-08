package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.clevertec.check.dto.CardDto;
import ru.clevertec.check.service.CardService;
import ru.clevertec.check.servlets.discountCard.DiscountCardServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@DisplayName("Discount card servlet tests")
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class DiscountCardServletTest extends AbstractServletTests{
    @Mock
    private CardService cardService;

    @InjectMocks
    private DiscountCardServlet discountCardServlet;

    @Test
    @DisplayName("get method")
    void getCardOkStatus() throws Exception {
        Integer id = 1;
        CardDto cardDto = new CardDto();
        request.setAttribute("id", id.toString());
        PrintWriter printWriter = new PrintWriter(cardDto.toString());

        when(request.getParameter(anyString())).thenReturn("1");
        when(cardService.getById(id)).thenReturn(cardDto);
        when(response.getWriter()).thenReturn(printWriter);
        when(objectMapper.writeValueAsString(cardDto)).thenReturn(cardDto.toString());
        discountCardServlet.doGet(request, response);

        verify(response).setStatus(SC_OK);
        verify(cardService).getById(id);
        verify(response).setContentType("application/json");
        verify(objectMapper).writeValueAsString(cardDto);
    }

    @Test
    @DisplayName("card save method")
    void saveCard() throws Exception {
        CardDto cardDto = new CardDto();

        when(request.getReader()).thenReturn(createBufferReader(cardDto));
        doNothing().when(validator).validateDiscountCard(cardDto);
        doNothing().when(cardService).save(cardDto);

        discountCardServlet.doPost(request, response);

        verify(response).setStatus(SC_CREATED);
    }

    @Test
    @DisplayName("pud update")
    void putUpdateCard() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getReader()).thenReturn(createBufferReader(new CardDto()));
        CardDto cardDto = new CardDto();
        when(objectMapper.readValue(anyString(), eq(CardDto.class))).thenReturn(cardDto);
        doNothing().when(validator).validateDiscountCard(any(CardDto.class));
        when(cardService.getById(1)).thenReturn(cardDto);

        discountCardServlet.doPut(request, response);

        verify(cardService).fullUpdateCard(cardDto, 1);
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    @DisplayName("delete card")
    void cardDelete() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        CardDto cardDto = new CardDto();
        when(cardService.getById(1)).thenReturn(cardDto);

        discountCardServlet.doDelete(request, response);

        verify(cardService).deleteById(1);
        verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    public BufferedReader createBufferReader(CardDto userDto) throws IOException {
        String jsonString = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(userDto);
        return new BufferedReader(new StringReader(jsonString));
    }
}