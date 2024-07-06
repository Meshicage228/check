package ru.clevertec.check.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDto {
    private Integer id;
    private String description;
    private Float price;
    private Integer quantity;
    private Integer purchaseQuantity;
    private Float individualDiscount;
    private Float fullCost;
    private Boolean isWholeSale;
}
