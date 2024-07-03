package ru.clevertec.check.service.impl;

import ru.clevertec.check.db.CustomDB;
import ru.clevertec.check.domain.DiscountCard;
import ru.clevertec.check.service.CardService;

public class CardServiceImpl implements CardService {
    public DiscountCard formCard(String cardNumber) {
        if(cardNumber.isBlank()){
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
