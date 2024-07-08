package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.dto.CardDto;
import ru.clevertec.check.entity.DiscountCardEntity;
import ru.clevertec.check.exceptions.ResourceNotFoundException;
import ru.clevertec.check.mapper.CardMapper;
import ru.clevertec.check.repository.CardRepository;
import ru.clevertec.check.service.impl.CardServiceImpl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@DisplayName("Card service tests")
@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardMapper cardMapper;

    @InjectMocks
    private CardServiceImpl cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("get card by number successfully")
    public void getCardByNumberSuccess() {
        Integer cardNumber = 12345;
        DiscountCardEntity card = DiscountCardEntity.builder().discountAmount(5).number(cardNumber).build();
        CardDto cardDto = new CardDto(cardNumber, 5);

        when(cardRepository.getByCardNumber(cardNumber)).thenReturn(card);
        when(cardMapper.toDto(card)).thenReturn(cardDto);

        CardDto result = cardService.formCard(cardNumber);

        assertThat(result).isNotNull();
        assertThat(result.getDiscountCard()).isEqualTo(cardNumber);
        assertThat(result.getDiscountAmount()).isEqualTo(5);
    }

    @Test
    @DisplayName("return null with null number")
    public void returnNullWhileNumberIsNull() {
        CardDto result = cardService.formCard(null);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("default card while number is missing in db")
    public void returnDefaultCard() {
        Integer cardNumber = 123456;

        when(cardRepository.getByCardNumber(cardNumber)).thenReturn(null);

        CardDto result = cardService.formCard(cardNumber);

        assertThat(result).isNotNull();
        assertThat(result.getDiscountCard()).isEqualTo(cardNumber);
        assertThat(result.getDiscountAmount()).isEqualTo(2);
    }

    @Test
    @DisplayName("get by id successfully")
    public void getByIdSuccessfully() throws ResourceNotFoundException {
        Integer id = 1;
        DiscountCardEntity card = DiscountCardEntity.builder().id(id).discountAmount(10).build();
        CardDto expectedDto = new CardDto(id, 10);

        when(cardRepository.getById(id)).thenReturn(card);
        when(cardMapper.toDto(card)).thenReturn(expectedDto);

        CardDto result = cardService.getById(id);

        assertThat(result).isEqualToComparingFieldByField(expectedDto);
    }

    @Test
    @DisplayName("get by id exception")
    public void getByIdException() throws ResourceNotFoundException {
        Integer id = 99;
        when(cardRepository.getById(id)).thenThrow(ResourceNotFoundException.class);

        assertThatThrownBy(() -> cardService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
