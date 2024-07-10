package ru.clevertec.check.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import ru.clevertec.check.utils.markers.DefaultCheckMarker;
import ru.clevertec.check.utils.markers.OnFormBillMarker;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties(value = {"purchaseQuantity", "individualDiscount", "fullCost"})
public class ProductDto {
    @Positive(groups = OnFormBillMarker.class)
    @NotNull(groups = OnFormBillMarker.class)
    private Integer id;
    @NotNull(groups = DefaultCheckMarker.class)
    private String description;
    @NotNull(groups = DefaultCheckMarker.class)
    private Float price;
    @NotNull(groups = {OnFormBillMarker.class, DefaultCheckMarker.class})
    @Positive(groups = {OnFormBillMarker.class, DefaultCheckMarker.class})
    private Integer quantity;
    private Integer purchaseQuantity;
    private Float individualDiscount;
    private Float fullCost;
    @NotNull(groups = DefaultCheckMarker.class)
    private Boolean isWholesale;
}
