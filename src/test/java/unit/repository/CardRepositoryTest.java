package unit.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.config.DataBaseConfig;
import ru.clevertec.check.dto.CardDto;
import ru.clevertec.check.entity.DiscountCardEntity;
import ru.clevertec.check.exceptions.ResourceNotFoundException;
import ru.clevertec.check.repository.CardRepository;
import java.sql.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Card repository tests")
@ExtendWith(MockitoExtension.class)
class CardRepositoryTest extends DBconnection{
    @Spy
    private CardRepository cardRepository;

    @BeforeEach
    public void setUp() throws SQLException {
        cardRepository = new CardRepository();

        when(DataBaseConfig.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    @DisplayName("get existing discount card by number")
    public void getExistingCard() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("number")).thenReturn(123456);
        when(resultSet.getInt("amount")).thenReturn(10);

        DiscountCardEntity result = cardRepository.getByCardNumber(123456);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(123456, result.getNumber());
        assertEquals(10, result.getDiscountAmount());
    }

    @Test
    @DisplayName("get by id success")
    public void getByIdSuccess() throws SQLException, ResourceNotFoundException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("number")).thenReturn(123456);
        when(resultSet.getInt("amount")).thenReturn(10);

        DiscountCardEntity result = cardRepository.getById(1);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getNumber()).isEqualTo(123456);
        assertThat(result.getDiscountAmount()).isEqualTo(10);
    }

    @Test
    @DisplayName("save card success valid data")
    public void saveCardSuccessData() throws SQLException {
        CardDto cardDto = new CardDto(123, 10);

        cardRepository.save(cardDto);

        verify(preparedStatement).setInt(1, cardDto.getDiscountCard());
        verify(preparedStatement).setInt(2, cardDto.getDiscountAmount());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    @DisplayName("delete card success valid data")
    public void deleteCardSuccessData() throws SQLException {
        Integer id = 123;

        cardRepository.deleteById(id);

        verify(preparedStatement).setInt(1, id);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    @DisplayName("update card valid data success")
    public void updateCardSuccessData() throws SQLException {
        CardDto cardDto = new CardDto(123, 10);
        int id = 456;

        cardRepository.update(cardDto, id);

        verify(preparedStatement).setInt(1, cardDto.getDiscountCard());
        verify(preparedStatement).setInt(2, cardDto.getDiscountAmount());
        verify(preparedStatement).setInt(3, id);
        verify(preparedStatement).executeUpdate();
    }

    @Nested
    @DisplayName("Check methods throw an exceptions")
    class CheckExceptions{

        @Test
        @DisplayName("get by id : Resource not found exception")
        public void recourseNotFound() throws SQLException {
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            assertThrows(ResourceNotFoundException.class, () -> cardRepository.getById(123));
        }

        @Test
        @DisplayName("Runtime exceptions")
        public void runtimeCheck() throws SQLException {
            when(preparedStatement.executeQuery()).thenThrow(new SQLException());
            when(preparedStatement.executeUpdate()).thenThrow(new SQLException());

            assertThrows(RuntimeException.class,
                    () -> cardRepository.save(new CardDto(123, 456)));
            assertThrows(RuntimeException.class,
                    () -> cardRepository.deleteById(123));
            assertThrows(RuntimeException.class,
                    () -> cardRepository.update(new CardDto(123, 456), 123));
            assertThrows(RuntimeException.class, () -> cardRepository.getById(123));
            assertThrows(RuntimeException.class, () -> cardRepository.getByCardNumber(123));
        }
    }
}
