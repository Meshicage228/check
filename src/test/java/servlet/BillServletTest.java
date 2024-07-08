/*
package servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.dto.UserDto;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.service.ClientService;
import ru.clevertec.check.servlets.bill.BillServlet;
import ru.clevertec.check.utils.InputDataValidator;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Bill servlet tests")
@ExtendWith(MockitoExtension.class)
class BillServletTest extends AbstractServletTests{

    @Mock
    private ClientService clientService;

    @InjectMocks
    private BillServlet clientServiceServlet;

    private static UserDto globalUser;

    @BeforeAll
    public static void setUp() {
        ArrayList<ProductDto> productDtos = new ArrayList<>();
        productDtos.add(ProductDto.builder()
                .id(1)
                .quantity(2)
                .build());

        globalUser = UserDto.builder()
                .products(productDtos)
                .discountCard(12345)
                .balanceDebitCard(1000f)
                .build();
    }

    @Test
    void doPost_ShouldGenerateFileAndSetResponse_WhenRequestIsValid() throws Exception {
        File mockFile = new File("test");
        InputStream mockInputStream = new ByteArrayInputStream(new byte[1048]);
        OutputStream mockOutputStream = new ByteArrayOutputStream();

        when(request.getReader()).thenReturn(createReader());
        when(objectMapper.readValue(anyString(), eq(UserDto.class))).thenReturn(globalUser);
        when(clientService.formClient(any(UserDto.class))).thenReturn(globalUser);
        when(clientService.formTotalBill(any(UserDto.class))).thenReturn(mockFile);
        when(clientService.formTotalBill(any(UserDto.class))).thenReturn(mockFile);
        doNothing().when(new FileInputStream(mockFile));
        clientServiceServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void doPost_ShouldSetBadRequestStatus_WhenValidatorThrowsBadRequestException() throws Exception {
        UserDto userDto = mock(UserDto.class);

        when(objectMapper.readValue(anyString(), eq(UserDto.class))).thenReturn(userDto);
        doThrow(new BadRequestException()).when(validator).validateUserDto(any(UserDto.class));

        clientServiceServlet.doPost(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);
    }

    private static BufferedReader createReader() throws IOException {
        String s = new ObjectMapper().writer().writeValueAsString(globalUser);
        return new BufferedReader(new StringReader(s));
    }
}*/
