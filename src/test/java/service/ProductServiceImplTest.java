package service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.entity.ProductEntity;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.exceptions.ResourceNotFoundException;
import ru.clevertec.check.repository.ProductRepository;
import ru.clevertec.check.service.impl.ProductServiceImpl;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Product service tests")
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("success cart")
    public void formCartSuccessfully() throws BadRequestException, ResourceNotFoundException {
        ArrayList<ProductDto> productDtos = new ArrayList<>();
        productDtos.add(ProductDto.builder().id(1).quantity(5).build());
        productDtos.add(ProductDto.builder().id(1).quantity(3).build());
        productDtos.add(ProductDto.builder().id(2).quantity(2).build());

        ProductEntity product1 = ProductEntity.builder()
                .id(1)
                .quantity(10)
                .build();
        ProductEntity product2 = ProductEntity.builder()
                .id(2)
                .quantity(5)
                .build();

        when(productRepository.getById(1)).thenReturn(product1);
        when(productRepository.getById(2)).thenReturn(product2);

        ArrayList<ProductDto> result = productService.formCart(productDtos);

        assertThat(result).hasSize(2);
        assertThat(result).extracting("id").containsOnly(1, 2);
        assertThat(result).extracting("purchaseQuantity").containsOnly(8, 2);
    }

    @Test
    @DisplayName("another quantity exception")
    public void anotherQuantityException() throws ResourceNotFoundException {
        ArrayList<ProductDto> productDtos = new ArrayList<>();
        productDtos.add(ProductDto.builder().id(1).quantity(20).build());

        ProductEntity product1 = ProductEntity.builder()
                .id(1)
                .quantity(2)
                .build();

        when(productRepository.getById(1)).thenReturn(product1);

        assertThatThrownBy(() -> productService.formCart(productDtos))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("get by id success")
    public void getByIdSuccess() throws ResourceNotFoundException {
        ProductEntity product = ProductEntity.builder().id(1).quantity(10).build();
        ProductDto expectedDto = ProductDto.builder().id(1).quantity(10).build();

        when(productRepository.getById(1)).thenReturn(product);

        ProductDto result = productService.getById(1);

        assertThat(result.getId()).isEqualTo(expectedDto.getId());
        assertThat(result.getQuantity()).isEqualTo(expectedDto.getQuantity());
    }

    @Test
    @DisplayName("get by id throw exception")
    public void getByIdThrow() throws ResourceNotFoundException {
        when(productRepository.getById(12)).thenThrow(ResourceNotFoundException.class);

        assertThatThrownBy(() -> productService.getById(12))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
