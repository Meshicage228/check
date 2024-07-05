package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.domain.Product;
import ru.clevertec.check.exceptions.InternalServerError;
import ru.clevertec.check.repository.ProductRepository;
import ru.clevertec.check.service.ProductService;
import ru.clevertec.check.service.impl.ProductServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Product service tests")
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService cartService;

    HashMap<Integer, Integer> pairs  = new HashMap<>() {{
        put(1, 1);
    }};

    private Product product = Product.builder()
            .price(5f)
            .quantity(12)
            .description("TestProduct")
            .build();

    @BeforeEach
    void setUp() {
        cartService = new ProductServiceImpl(productRepository);
    }

    @Test
    void successProductExtract() {
        when(productRepository.getById(1)).thenReturn(product);

        ArrayList<Product> result = cartService.formCart(pairs);

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(product);
    }

    @Test
    void productNotFound() {
        when(productRepository.getById(1)).thenReturn(null);

        assertThatThrownBy(() -> cartService.formCart(pairs))
                .isInstanceOf(InternalServerError.class);
    }

    @Test
    void quantityException() {
        pairs.replace(1, 200);

        when(productRepository.getById(1)).thenReturn(product);

        assertThatThrownBy(() -> cartService.formCart(pairs))
                .isInstanceOf(InternalServerError.class);
    }
}