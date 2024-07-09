package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.config.DataBaseConfig;
import ru.clevertec.check.dto.CardDto;
import ru.clevertec.check.entity.DiscountCardEntity;
import ru.clevertec.check.exceptions.ResourceNotFoundException;
import ru.clevertec.check.repository.CardRepository;
import java.sql.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Card repository tests")
@ExtendWith(MockitoExtension.class)
class CardRepositoryTest extends DBconnection{

    private CardRepository cardRepository;

    @BeforeEach
    public void setUp(){
        cardRepository = new CardRepository();
    }

    @Test
    @DisplayName("get existing discount card by number")
    public void getExistingCard() throws SQLException {
        given(DataBaseConfig.getConnection()).willReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
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
        when(DataBaseConfig.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
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
    @DisplayName("get by id exception")
    public void getByIdException() throws SQLException {
        when(DataBaseConfig.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        assertThatThrownBy(() -> cardRepository.getById(1))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("save card success valid data")
    void saveCardSuccessData() throws SQLException {
        CardDto cardDto = new CardDto(123, 10);
        when(DataBaseConfig.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        cardRepository.save(cardDto);

        verify(preparedStatement).setInt(1, cardDto.getDiscountCard());
        verify(preparedStatement).setInt(2, cardDto.getDiscountAmount());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    @DisplayName("delete card success valid data")
    void deleteCardSuccessData() throws SQLException {
        Integer id = 123;
        when(DataBaseConfig.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        cardRepository.deleteById(id);

        verify(preparedStatement).setInt(1, id);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    @DisplayName("update card valid data success")
    void updateCardSuccessData() throws SQLException {
        CardDto cardDto = new CardDto(123, 10);
        Integer id = 456;
        when(DataBaseConfig.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        cardRepository.update(cardDto, id);

        verify(preparedStatement).setInt(1, cardDto.getDiscountCard());
        verify(preparedStatement).setInt(2, cardDto.getDiscountAmount());
        verify(preparedStatement).setInt(3, id);
        verify(preparedStatement).executeUpdate();
    }
}
