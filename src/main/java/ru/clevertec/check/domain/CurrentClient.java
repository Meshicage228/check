package ru.clevertec.check.domain;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@Builder
public class CurrentClient {
    public ArrayList<Product> basket;
    public DiscountCard discountDebitCard;
    public Float balanceDebitCard;
}
