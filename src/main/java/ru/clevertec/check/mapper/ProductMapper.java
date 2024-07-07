package ru.clevertec.check.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.check.entity.ProductEntity;
import ru.clevertec.check.dto.ProductDto;

@Mapper
public interface ProductMapper {
    ProductDto toDto(ProductEntity product);
}
