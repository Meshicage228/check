package ru.clevertec.check.service;

import ru.clevertec.check.domain.DiscountCard;
import ru.clevertec.check.db.CustomDB;

public class CardService {
    public DiscountCard formCard(String cardNumber) {

        return CustomDB.discountCards.containsKey(cardNumber) ?
                DiscountCard.builder()
                        .discountAmount(CustomDB.discountCards.get(cardNumber))
                        .number(cardNumber).build() :
                DiscountCard.builder()
                        .discountAmount(2)
                        .number(cardNumber).build();

    }
}
