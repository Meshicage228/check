package ru.clevertec.check.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class Product {
    private String description;
    private Float price;
    private Integer quantity;
    private Integer purchaseQuantity;
    private Float individualDiscount;
    private Float fullCost;
    private Boolean isWholeSale;
}
