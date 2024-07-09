package service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.*;

@DisplayName("Client service tests")
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
    @Mock
    private ProductService productService;

    @Mock
    private CardService cardService;

    @Mock
    private FilePrintServiceImpl fileService;

    @InjectMocks
    private ClientServiceImpl userService;

    @Test
    @DisplayName("create user dto")
    public void createUserDto() throws BadRequestException, ResourceNotFoundException {
        UserDto userDto = mock(UserDto.class);
        ArrayList<ProductDto> products = new ArrayList<>(Arrays.asList(new ProductDto()));
        CardDto cardDto = new CardDto();

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
        UserDto userDto = mock(UserDto.class);
        ProductDto productDto = ProductDto.builder()
                .quantity(10)
                .purchaseQuantity(1)
                .price(10f)
                .isWholesale(true)
                .description("test")
                .build();
        ArrayList<ProductDto> basket = new ArrayList<>(Arrays.asList(productDto));
        CardDto discountDebitCard = CardDto.builder()
                .discountCard(123)
                .discountAmount(5)
                .build();
        File billFile = mock(File.class);

        when(userDto.getProducts()).thenReturn(basket);
        when(userDto.getCardDto()).thenReturn(discountDebitCard);
        when(userDto.getBalanceDebitCard()).thenReturn(1000f);
        doNothing().when(productService).decreaseProductAmount(basket);
        when(fileService.createBillFile(any(UserDto.class), anyFloat(), anyFloat(), anyFloat())).thenReturn(billFile);

        File result = userService.formTotalBill(userDto);

        assertNotNull(result);
        assertEquals(billFile, result);
    }

    @Test
    @DisplayName("Not enough money exception")
    public void notEnoughMoneyException() {
        UserDto userDto = mock(UserDto.class);
        ProductDto productDto = ProductDto.builder()
                .quantity(10)
                .purchaseQuantity(2)
                .price(10f)
                .isWholesale(true)
                .description("test")
                .build();
        ArrayList<ProductDto> basket = new ArrayList<>(Arrays.asList(productDto));
        CardDto discountDebitCard = CardDto.builder()
                .discountCard(123)
                .discountAmount(5)
                .build();

        when(userDto.getProducts()).thenReturn(basket);
        when(userDto.getCardDto()).thenReturn(discountDebitCard);
        when(userDto.getBalanceDebitCard()).thenReturn(0f);

        assertThrows(NotEnoughMoneyException.class, () -> userService.formTotalBill(userDto));
    }
}