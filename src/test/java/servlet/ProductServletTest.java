package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    void getProductSuccess() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(productService.getById(1)).thenReturn(new ProductDto());

        productServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    @DisplayName("put update product")
    void putProductSuccess() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getReader()).thenReturn(createBufferReader(new ProductDto()));
        when(objectMapper.readValue(anyString(), eq(ProductDto.class))).thenReturn(new ProductDto());

        productServlet.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    @DisplayName("post product")
    void postProduct() throws Exception {
        when(request.getReader()).thenReturn(createBufferReader(new ProductDto()));
        when(objectMapper.readValue(anyString(), eq(ProductDto.class))).thenReturn(new ProductDto());

        productServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    @DisplayName("delete product")
    void deleteProduct() {
        when(request.getParameter("id")).thenReturn("1");

        productServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    public BufferedReader createBufferReader(ProductDto userDto) throws IOException {
        String jsonString = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(userDto);
        return new BufferedReader(new StringReader(jsonString));
    }
}