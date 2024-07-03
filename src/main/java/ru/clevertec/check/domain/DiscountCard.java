package ru.clevertec.check.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class DiscountCard {
    private String number;
    private Integer discountAmount;
}
