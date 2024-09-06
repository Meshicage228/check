package unit.util;

import ru.clevertec.check.dto.ProductDto;

public class ProjectObjectsFactory {

    public ProductDto createProductDto(){
        return ProductDto.builder()
                .quantity(10)
                .purchaseQuantity(2)
                .price(10f)
                .id(1)
                .isWholesale(true)
                .description("test")
                .build();
    }
}
