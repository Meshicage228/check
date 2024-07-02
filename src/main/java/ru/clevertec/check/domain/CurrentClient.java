package ru.clevertec.check.domain;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@Builder
public class CurrentClient {
    private ArrayList<Product> basket;
    private DiscountCard discountDebitCard;
    private Float balanceDebitCard;
}
