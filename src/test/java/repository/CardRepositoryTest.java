package repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.config.DataBaseConfig;
import ru.clevertec.check.domain.DiscountCard;
import ru.clevertec.check.repository.CardRepository;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@DisplayName("Card repository tests")
@ExtendWith(MockitoExtension.class)
class CardRepositoryTest extends DBconnection{

    @Test
    @DisplayName("get existing discount card")
    public void getExistingCard() throws SQLException {
        CardRepository cardRepository = new CardRepository();

        given(DataBaseConfig.getConnection()).willReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("number")).thenReturn(123456);
        when(resultSet.getInt("amount")).thenReturn(10);

        DiscountCard result = cardRepository.getByCardNumber(123456);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(123456, result.getNumber());
        assertEquals(10, result.getDiscountAmount());
    }
}
