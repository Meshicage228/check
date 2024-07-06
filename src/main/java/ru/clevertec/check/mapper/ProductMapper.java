package ru.clevertec.check.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.check.domain.Product;
import ru.clevertec.check.dto.ProductDto;

@Mapper
public interface ProductMapper {
    ProductDto toDto(Product product);

    Product toEntity(ProductDto product);
}
