package ru.clevertec.check.domain;

import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@ToString
@Builder
public class CurrentClient {
    public HashMap<Integer, Product> basket;
    public DiscountCard discountDebitCard;
    public Float balanceDebitCard;
}
