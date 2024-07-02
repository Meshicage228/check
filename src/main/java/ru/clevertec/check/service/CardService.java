package ru.clevertec.check.service;

import ru.clevertec.check.db.CustomDB;
import ru.clevertec.check.domain.DiscountCard;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class CardService {
    public DiscountCard formCard(String cardNumber) {
        if(isBlank(cardNumber)){
            return null;
        }
        return CustomDB.discountCards.containsKey(cardNumber) ?
                DiscountCard.builder()
                        .discountAmount(CustomDB.discountCards.get(cardNumber))
                        .number(cardNumber).build() :
                DiscountCard.builder()
                        .discountAmount(2)
                        .number(cardNumber).build();

    }
}
