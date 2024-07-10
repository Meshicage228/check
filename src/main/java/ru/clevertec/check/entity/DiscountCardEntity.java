package ru.clevertec.check.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class DiscountCardEntity {
    private Integer id;
    private Integer number;
    private Integer discountAmount;
}
