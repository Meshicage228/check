package unit.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.config.DataBaseConfig;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.entity.ProductEntity;
import ru.clevertec.check.exceptions.ResourceNotFoundException;
import ru.clevertec.check.repository.ProductRepository;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Product repository tests")
@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest extends DBconnection {
    @Spy
    private ProductRepository productRepository;

    private ProductDto productDto;

    @BeforeEach
    public void setUp() throws SQLException {
        productDto = ProductDto.builder()
                .id(1)
                .description("Description")
                .price(100f)
                .purchaseQuantity(2)
                .quantity(10)
                .isWholesale(true)
                .build();

        when(DataBaseConfig.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    @DisplayName("get existing product by id")
    public void getProductCart() throws SQLException, ResourceNotFoundException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("description")).thenReturn("test");
        when(resultSet.getFloat("price")).thenReturn(12f);
        when(resultSet.getInt("quantity_in_stock")).thenReturn(12);
        when(resultSet.getBoolean("wholesale_product")).thenReturn(true);

        ProductEntity result = productRepository.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("test", result.getDescription());
        assertEquals(12f, result.getPrice());
        assertEquals(12, result.getQuantity());
        assertEquals(true, result.getIsWholesale());
    }

    @Test
    @DisplayName("save product valid data")
    public void saveProductSuccess() throws SQLException {
        productRepository.save(productDto);

        verify(preparedStatement).executeUpdate();
    }

    @Test
    @DisplayName("delete product valid data")
    public void deleteProductValidData() throws SQLException {
        productRepository.deleteById(1);

        verify(preparedStatement).executeUpdate();
    }

    @Test
    @DisplayName("update product valid data")
    public void updateValidData() throws SQLException {
        productRepository.update(productDto, 1);

        verify(preparedStatement).executeUpdate();
    }

    @Test
    @DisplayName("decrease amount valid data")
    public void decreaseAmountProductValidData() throws ResourceNotFoundException, SQLException {
        ArrayList<ProductDto> basket = new ArrayList<>();
        basket.add(productDto);

        ProductEntity build = ProductEntity.builder()
                .id(1)
                .quantity(100)
                .build();

        when(resultSet.next()).thenReturn(true);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(productRepository.getById(anyInt())).thenReturn(build);

        productRepository.decreaseAmount(basket);

        verify(preparedStatement).executeUpdate();
    }

    @Nested
    @DisplayName("Check methods throw an exceptions")
    class CheckExceptions {
        @Test
        @DisplayName("check ResourceNotFound exception")
        public void getById_ResourceNotFoundException() throws SQLException {
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            assertThrows(ResourceNotFoundException.class, () -> productRepository.getById(1));
        }

        @Test
        @DisplayName("check Runtime exceptions")
        public void save_RuntimeException() throws SQLException{
            when(preparedStatement.executeUpdate()).thenThrow(new SQLException());
            when(preparedStatement.executeQuery()).thenThrow(new SQLException());

            assertThrows(RuntimeException.class, () -> productRepository.save(productDto));
            assertThrows(RuntimeException.class, () -> productRepository.deleteById(1));
            assertThrows(RuntimeException.class, () -> productRepository.update(productDto, 1));
            assertThrows(RuntimeException.class, () -> productRepository.getById(1));
        }
    }
}
