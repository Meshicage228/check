package unit.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.service.ProductService;
import ru.clevertec.check.servlets.product.ProductServlet;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Product servlet tests")
@ExtendWith(MockitoExtension.class)
class ProductServletTest extends AbstractServletTests{
    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductServlet productServlet;

    @Test
    @DisplayName("get product")
    public void getProductSuccess() throws Exception {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        ProductDto productDto = ProductDto.builder()
                .id(1)
                .quantity(10)
                .build();
        String json = new ObjectMapper().writeValueAsString(productDto);

        when(request.getParameter("id")).thenReturn("1");
        when(productService.getById(1)).thenReturn(productDto);
        when(response.getWriter()).thenReturn(writer);
        when(objectMapper.writeValueAsString(productDto)).thenReturn(json);

        productServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response.getWriter()).write(captor.capture());
        assertEquals(json, captor.getValue());
    }

    @Test
    @DisplayName("put update product")
    public void putProductSuccess() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getReader()).thenReturn(createBufferReader(new ProductDto()));
        when(objectMapper.readValue(anyString(), eq(ProductDto.class))).thenReturn(new ProductDto());

        productServlet.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    @DisplayName("post product")
    public void postProduct() throws Exception {
        when(request.getReader()).thenReturn(createBufferReader(new ProductDto()));
        when(objectMapper.readValue(anyString(), eq(ProductDto.class))).thenReturn(new ProductDto());

        productServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    @DisplayName("delete product")
    public void deleteProduct() {
        when(request.getParameter("id")).thenReturn("1");

        productServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    public BufferedReader createBufferReader(ProductDto userDto) throws IOException {
        String jsonString = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(userDto);
        return new BufferedReader(new StringReader(jsonString));
    }
}