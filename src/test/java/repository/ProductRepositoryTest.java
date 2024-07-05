package repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.config.DataBaseConfig;
import ru.clevertec.check.domain.Product;
import ru.clevertec.check.repository.ProductRepository;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@DisplayName("Product repository tests")
@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest extends DBconnection {

    @Test
    @DisplayName("get existing product by id")
    public void getProductCart() throws SQLException {
        ProductRepository productRepository = new ProductRepository();

        given(DataBaseConfig.getConnection()).willReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("description")).thenReturn("test");
        when(resultSet.getFloat("price")).thenReturn(12f);
        when(resultSet.getInt("quantity_in_stock")).thenReturn(12);
        when(resultSet.getBoolean("wholesale_product")).thenReturn(true);

        Product result = productRepository.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("test", result.getDescription());
        assertEquals(12f, result.getPrice());
        assertEquals(12, result.getQuantity());
        assertEquals(true, result.getIsWholeSale());
    }
}
