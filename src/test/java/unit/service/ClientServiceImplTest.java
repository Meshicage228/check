package unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.dto.CardDto;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.dto.UserDto;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.exceptions.NotEnoughMoneyException;
import ru.clevertec.check.exceptions.ResourceNotFoundException;
import ru.clevertec.check.service.CardService;
import ru.clevertec.check.service.ProductService;
import ru.clevertec.check.service.impl.ClientServiceImpl;
import ru.clevertec.check.service.impl.FilePrintServiceImpl;
import unit.util.ProjectObjectsFactory;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.*;

@DisplayName("Client service tests")
@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
    @Mock
    private ProductService productService;

    @Mock
    private CardService cardService;

    @Mock
    private UserDto userDto;

    @Mock
    private FilePrintServiceImpl fileService;

    @InjectMocks
    private ClientServiceImpl userService;

    private ArrayList<ProductDto> products;

    private final ProjectObjectsFactory factory = new ProjectObjectsFactory();

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
    }

    @Test
    @DisplayName("create user dto")
    public void createUserDto() throws BadRequestException, ResourceNotFoundException {
        CardDto cardDto = new CardDto();
        products.add(new ProductDto());

        when(userDto.getProducts()).thenReturn(products);
        when(userDto.getDiscountCard()).thenReturn(123);
        when(productService.formCart(products)).thenReturn(products);
        when(cardService.formCard(123)).thenReturn(cardDto);

        UserDto result = userService.formClient(userDto);

        assertNotNull(result);
        assertEquals(products, result.getProducts());
        assertEquals(cardDto, result.getCardDto());
    }

    @Test
    @DisplayName("create bill")
    public void fromTotalBill() throws Exception {
        ProductDto productDto = factory.createProductDto();
        products.add(productDto);
        CardDto discountDebitCard = CardDto.builder()
                .discountCard(123)
                .discountAmount(5)
                .build();
        File billFile = mock(File.class);

        when(userDto.getProducts()).thenReturn(products);
        when(userDto.getCardDto()).thenReturn(discountDebitCard);
        when(userDto.getBalanceDebitCard()).thenReturn(1000f);
        doNothing().when(productService).decreaseProductAmount(products);
        when(fileService.createBillFile(any(UserDto.class), anyFloat(), anyFloat(), anyFloat())).thenReturn(billFile);

        File result = userService.formTotalBill(userDto);

        assertNotNull(result);
        assertEquals(billFile, result);
    }

    @Test
    @DisplayName("Not enough money exception")
    public void notEnoughMoneyException() {
        ProductDto productDto = factory.createProductDto();
        products.add(productDto);

        CardDto discountDebitCard = CardDto.builder()
                .discountCard(123)
                .discountAmount(5)
                .build();

        when(userDto.getProducts()).thenReturn(products);
        when(userDto.getCardDto()).thenReturn(discountDebitCard);
        when(userDto.getBalanceDebitCard()).thenReturn(0f);

        assertThrows(NotEnoughMoneyException.class, () -> userService.formTotalBill(userDto));
    }
}