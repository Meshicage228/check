package unit.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.dto.UserDto;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.service.ClientService;
import ru.clevertec.check.servlets.bill.BillServlet;
import java.io.*;
import java.util.ArrayList;

import static javax.servlet.http.HttpServletResponse.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Bill servlet tests")
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class BillServletTest extends AbstractServletTests{

    @Mock
    private ClientService clientService;

    @InjectMocks
    @Spy
    private BillServlet billServlet;

    private UserDto globalUser;

    @BeforeEach
    public void setUp() {
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
    @DisplayName("created file successfully")
    void fileCreatedSuccessfully() throws Exception {
        File mockFile = Mockito.mock(File.class);

        when(request.getReader()).thenReturn(createReader(globalUser));
        when(objectMapper.readValue(anyString(), eq(UserDto.class))).thenReturn(globalUser);
        when(clientService.formClient(any(UserDto.class))).thenReturn(globalUser);
        when(clientService.formTotalBill(any(UserDto.class))).thenReturn(mockFile);
        doNothing().when(billServlet).printFile(response, mockFile);
        billServlet.doPost(request, response);

        verify(response).setStatus(SC_OK);
    }

    @Test
    @DisplayName("wrong data")
    void validatorThrow() throws Exception {
        when(request.getReader()).thenReturn(createReader(globalUser));
        when(objectMapper.readValue(anyString(), eq(UserDto.class))).thenReturn(globalUser);
        doThrow(new BadRequestException()).when(validator).validateUserDto(any(UserDto.class));

        billServlet.doPost(request, response);

        verify(response).setStatus(SC_BAD_REQUEST);
    }

    private static BufferedReader createReader(UserDto globalUser) throws IOException {
        String s = new ObjectMapper().writer().writeValueAsString(globalUser);
        return new BufferedReader(new StringReader(s));
    }
}
