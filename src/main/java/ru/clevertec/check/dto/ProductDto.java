package ru.clevertec.check.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties(value = {"purchaseQuantity", "individualDiscount", "fullCost"})
public class ProductDto {
    private Integer id;
    private String description;
    private Float price;
    private Integer quantity;
    private Integer purchaseQuantity;
    private Float individualDiscount;
    private Float fullCost;
    private Boolean isWholesale;
}
