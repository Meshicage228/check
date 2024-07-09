package ru.clevertec.check.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CardDto {
    private Integer discountCard;
    private Integer discountAmount;
}
