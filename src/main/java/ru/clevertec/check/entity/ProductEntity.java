package ru.clevertec.check.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class ProductEntity {
    private Integer id;
    private String description;
    private Float price;
    private Integer quantity;
    private Boolean isWholesale;
}
