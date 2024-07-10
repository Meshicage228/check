package ru.clevertec.check.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CardDto {
    @NotNull
    private Integer discountCard;
    @NotNull
    private Integer discountAmount;
}
