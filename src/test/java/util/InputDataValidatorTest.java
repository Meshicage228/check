package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.dto.CardDto;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.dto.UserDto;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.utils.InputDataValidator;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Data validator tests")
@ExtendWith(MockitoExtension.class)
class InputDataValidatorTest {

    private InputDataValidator inputDataValidator;

    @BeforeEach
    public void setUp() {
        inputDataValidator = new InputDataValidator();
    }

    @Test
    @DisplayName("throw when card number is null")
    void cardNumberException() {
        CardDto cardDto = new CardDto(null, 123456);

        assertThatThrownBy(() -> inputDataValidator.validateDiscountCard(cardDto))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("throw when productDto is null")
    void productDtoNull() {
        assertThatThrownBy(() -> inputDataValidator.validateProductDto(null))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("throw when any product field is null")
    void productAnyFiledNull() {
        ProductDto productDto = ProductDto.builder()
                .purchaseQuantity(null)
                .price(null)
                .description(null)
                .isWholesale(false)
                .build();

        assertThatThrownBy(() -> inputDataValidator.validateProductDto(productDto))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("empty products")
    void userDtoNullProducts() {
        UserDto userDto = UserDto.builder()
                .products(null)
                .build();

        assertThatThrownBy(() -> inputDataValidator.validateUserDto(userDto))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("empty products")
    void userDtoEmptyProducts() {
        UserDto userDto = UserDto.builder()
                .products(new ArrayList<>())
                .build();

        assertThatThrownBy(() -> inputDataValidator.validateUserDto(userDto))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("null product field")
    void userDtoAnyNullProductField() {
        ArrayList<ProductDto> products = new ArrayList<>();
        products.add(ProductDto.builder().id(null).build());
        UserDto userDto = UserDto.builder()
                .products(products)
                .build();

        assertThatThrownBy(() -> inputDataValidator.validateUserDto(userDto))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("null balance debit card")
    void userDtoNullBalanceCard() {
        ArrayList<ProductDto> products = new ArrayList<>();
        products.add(ProductDto.builder()
                .id(1)
                .quantity(2)
                .build());
        UserDto userDto = UserDto.builder()
                .products(products)
                .balanceDebitCard(null)
                .build();

        assertThatThrownBy(() -> inputDataValidator.validateUserDto(userDto))
                .isInstanceOf(BadRequestException.class);
    }

}