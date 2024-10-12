package ru.clevertec.check.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserDto {
    private ArrayList<ProductDto> products;
    private Integer discountCard;
    @JsonIgnore
    private CardDto cardDto;
    private Float balanceDebitCard;
}
