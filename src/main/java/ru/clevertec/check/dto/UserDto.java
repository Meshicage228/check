package ru.clevertec.check.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.clevertec.check.utils.markers.OnFormBillMarker;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserDto {
    @Valid
    private ArrayList<ProductDto> products;

    private Integer discountCard;
    @JsonIgnore
    private CardDto cardDto;
    @NotNull(groups = OnFormBillMarker.class)
    private Float balanceDebitCard;
}
