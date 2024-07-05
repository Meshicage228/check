package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.domain.DiscountCard;
import ru.clevertec.check.repository.CardRepository;
import ru.clevertec.check.service.CardService;
import ru.clevertec.check.service.impl.CardServiceImpl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@DisplayName("Card service tests")
@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    private CardService cardService;

    @BeforeEach
    void setUp() {
        cardService = new CardServiceImpl(cardRepository);
    }

    @Test
    @DisplayName("Null card")
    void nullCardNumber() {
        DiscountCard result = cardService.formCard(null);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Any other card 2%")
    void anyOtherCard() {
        String cardNumber = "12345";
        DiscountCard resultCard = DiscountCard.builder()
                .number(Integer.parseInt(cardNumber))
                .discountAmount(2)
                .build();

        when(cardRepository.getByCardNumber(anyInt())).thenReturn(null);

        DiscountCard result = cardService.formCard(cardNumber);

        assertThat(result).isNotNull();
        assertThat(result.getNumber()).isEqualTo(resultCard.getNumber());
        assertThat(result.getDiscountAmount()).isEqualTo(resultCard.getDiscountAmount());
    }

    @Test
    @DisplayName("Existing card 3-5%")
    void existingCard() {
        String cardNumber = "1111";

        DiscountCard resultCard = DiscountCard.builder()
                .number(Integer.parseInt(cardNumber))
                .discountAmount(3)
                .build();

        when(cardRepository.getByCardNumber(anyInt())).thenReturn(resultCard);

        DiscountCard result = cardService.formCard(cardNumber);

        assertThat(result).isNotNull();
        assertThat(result.getNumber()).isEqualTo(resultCard.getNumber());
        assertThat(result.getDiscountAmount()).isEqualTo(resultCard.getDiscountAmount());
    }
}