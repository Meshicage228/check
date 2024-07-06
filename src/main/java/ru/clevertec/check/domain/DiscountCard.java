package ru.clevertec.check.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class DiscountCard {
    private Integer id;
    private Integer number;
    private Integer discountAmount;
}
