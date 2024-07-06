package ru.clevertec.check.service.impl;

import lombok.AllArgsConstructor;
import ru.clevertec.check.domain.DiscountCard;
import ru.clevertec.check.repository.CardRepository;
import ru.clevertec.check.service.CardService;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@AllArgsConstructor
public class CardServiceImpl implements CardService {
    private CardRepository cardRepository;

    @Override
    public DiscountCard formCard(String cardNumber) {
        if(isNull(cardNumber)){
            return null;
        }
        DiscountCard byCardNumber = cardRepository.getByCardNumber(Integer.parseInt(cardNumber));

        return nonNull(byCardNumber) ? byCardNumber :
                DiscountCard.builder()
                        .discountAmount(2)
                        .number(Integer.parseInt(cardNumber)).build();
    }
}
