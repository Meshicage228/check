package unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.entity.ProductEntity;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.exceptions.ResourceNotFoundException;
import ru.clevertec.check.repository.ProductRepository;
import ru.clevertec.check.service.impl.ProductServiceImpl;
import unit.util.ProjectObjectsFactory;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Product service tests")
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Captor
    private ArgumentCaptor<ProductDto> productDtoCapture;

    @Captor
    private ArgumentCaptor<Integer> intCaptor;

    private final ProjectObjectsFactory factory = new ProjectObjectsFactory();

    private ArrayList<ProductDto> productDtos;

    @BeforeEach
    void setUp() {
        productDtos = new ArrayList<>();
    }

    @Test
    @DisplayName("success cart")
    public void formCartSuccessfully() throws BadRequestException, ResourceNotFoundException {
        productDtos.add(factory.createProductDto());
        productDtos.add(factory.createProductDto());

        ProductEntity product1 = ProductEntity.builder()
                .id(1)
                .quantity(30)
                .build();

        when(productRepository.getById(1)).thenReturn(product1);

        ArrayList<ProductDto> result = productService.formCart(productDtos);

        assertThat(result).hasSize(1);
        assertThat(result).extracting("id").containsOnly(1);
        assertThat(result).extracting("purchaseQuantity").containsOnly(20);
    }

    @Test
    @DisplayName("get by id success")
    public void getByIdSuccess() throws ResourceNotFoundException {
        ProductEntity product = ProductEntity.builder().id(1).quantity(10).build();
        ProductDto expectedDto = factory.createProductDto();

        when(productRepository.getById(1)).thenReturn(product);

        ProductDto result = productService.getById(1);

        assertThat(result.getId()).isEqualTo(expectedDto.getId());
        assertThat(result.getQuantity()).isEqualTo(expectedDto.getQuantity());
    }

    @Test
    @DisplayName("save method invoked correctly")
    public void save() {
        ProductDto workoutDto = new ProductDto();

        productService.save(workoutDto);

        verify(productRepository).save(productDtoCapture.capture());
        assertEquals(workoutDto, productDtoCapture.getValue());
    }

    @Test
    @DisplayName("delete by id method invoked correctly")
    public void deleteById() {
        Integer id = 1;

        productService.deleteById(id);

        verify(productRepository).deleteById(intCaptor.capture());
        assertEquals(id, intCaptor.getValue());
    }

    @Test
    @DisplayName("put update method invoked correctly")
    public void fullUpdateProduct() {
        ProductDto workoutDto = new ProductDto();
        Integer id = 1;

        productService.fullUpdateProduct(workoutDto, id);

        verify(productRepository).update(productDtoCapture.capture(), intCaptor.capture());
        assertEquals(workoutDto, productDtoCapture.getValue());
        assertEquals(id, intCaptor.getValue());
    }

    @Test
    @DisplayName("decrease Product amount method invoked correctly")
    public void decreaseProductAmount() throws ResourceNotFoundException {
        ArgumentCaptor<ArrayList<ProductDto>> captor = ArgumentCaptor.forClass(ArrayList.class);
        productDtos.add(new ProductDto());

        productService.decreaseProductAmount(productDtos);

        verify(productRepository).decreaseAmount(captor.capture());
        assertEquals(productDtos, captor.getValue());
    }

    @Test
    @DisplayName("get by id throw exception")
    public void getByIdThrow() throws ResourceNotFoundException {
        when(productRepository.getById(12)).thenThrow(ResourceNotFoundException.class);

        assertThatThrownBy(() -> productService.getById(12))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("another quantity exception")
    public void anotherQuantityException() throws ResourceNotFoundException {
        productDtos.add(factory.createProductDto());

        ProductEntity product1 = ProductEntity.builder()
                .id(1)
                .quantity(2)
                .build();

        when(productRepository.getById(1)).thenReturn(product1);

        assertThatThrownBy(() -> productService.formCart(productDtos))
                .isInstanceOf(BadRequestException.class);
    }
}
