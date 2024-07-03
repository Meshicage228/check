package ru.clevertec.check.service;

import ru.clevertec.check.domain.DiscountCard;

public interface CardService {
    DiscountCard formCard(String cardNumber);
}
