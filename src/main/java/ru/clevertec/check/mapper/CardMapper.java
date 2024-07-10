package ru.clevertec.check.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.check.entity.DiscountCardEntity;
import ru.clevertec.check.dto.CardDto;

@Mapper(
        componentModel = "spring"
)
public interface CardMapper {
    @Mapping(target = "discountCard", source = "number")
    CardDto toDto(DiscountCardEntity discountCard);
}
