package service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.domain.CurrentClient;
import ru.clevertec.check.domain.DiscountCard;
import ru.clevertec.check.domain.Product;
import ru.clevertec.check.exceptions.NotEnoughMoneyException;
import ru.clevertec.check.service.FilePrintService;
import ru.clevertec.check.service.impl.BillServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.*;

@DisplayName("Bill service tests")
@ExtendWith(MockitoExtension.class)
class BillServiceImplTest {

    @Mock
    private FilePrintService fileService;

    @Mock
    private CurrentClient currentClient;

    @Mock
    DiscountCard discountDebitCard;

    @InjectMocks
    private BillServiceImpl billingService;

    @Test
    @DisplayName("check bill statistics")
    public void getProductBillStatistics() throws NotEnoughMoneyException {
        Product product1 = Product.builder()
                .id(1)
                .description("Product 1")
                .price(10f)
                .purchaseQuantity(1)
                .quantity(2)
                .isWholeSale(false)
                .build();

        Product product2 = Product.builder()
                .id(2)
                .description("Product 2")
                .price(20f)
                .quantity(5)
                .purchaseQuantity(1)
                .isWholeSale(true)
                .build();

        ArrayList<Product> basket = new ArrayList<>(Arrays.asList(product1, product2));

        when(currentClient.getBasket()).thenReturn(basket);
        when(currentClient.getDiscountDebitCard()).thenReturn(discountDebitCard);
        when(discountDebitCard.getDiscountAmount()).thenReturn(5);
        when(currentClient.getBalanceDebitCard()).thenReturn(1000f);

        billingService.formTotalBill(currentClient);

        verify(fileService).createBillFile(eq(currentClient), any(Float.class), any(Float.class), any(Float.class));
        assertThat(product1.getIndividualDiscount()).isEqualTo(0.5f);
        assertThat(product2.getIndividualDiscount()).isEqualTo(1.0f);
        assertThat(product1.getFullCost()).isEqualTo(10.0f);
        assertThat(product2.getFullCost()).isEqualTo(20.0f);
    }

    @Test
    @DisplayName("not enough money")
    public void notEnoughMoney() {
        CurrentClient currentClient = mock(CurrentClient.class);
        ArrayList<Product> basket = new ArrayList<>();

        Product product1 = Product.builder()
                .id(1)
                .description("Product 1")
                .price(100f)
                .purchaseQuantity(5)
                .quantity(10)
                .isWholeSale(false)
                .build();

        basket.add(product1);

        when(currentClient.getBasket()).thenReturn(basket);
        when(currentClient.getBalanceDebitCard()).thenReturn(50f);

        Throwable thrown = catchThrowable(() -> billingService.formTotalBill(currentClient));

        assertThat(thrown).isInstanceOf(NotEnoughMoneyException.class);
    }
}