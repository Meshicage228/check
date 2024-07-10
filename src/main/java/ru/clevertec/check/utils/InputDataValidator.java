package ru.clevertec.check.utils;

import org.springframework.stereotype.Component;
import ru.clevertec.check.dto.CardDto;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.dto.UserDto;
import ru.clevertec.check.exceptions.BadRequestException;

import static java.util.Objects.isNull;

@Component
public class InputDataValidator {
    public void validateDiscountCard(CardDto cardDto) throws BadRequestException {
        if(isNull(cardDto.getDiscountAmount()) || isNull(cardDto.getDiscountCard())){
            throw new BadRequestException();
        }
    }

    public void validateProductDto(ProductDto productDto) throws BadRequestException {
        if(isNull(productDto)){
            throw new BadRequestException();
        }
        if(isNull(productDto.getDescription()) || isNull(productDto.getQuantity()) || isNull(productDto.getPrice()) || isNull(productDto.getIsWholesale())){
            throw new BadRequestException();
        }
    }

    public void validateUserDto(UserDto userDto) throws BadRequestException{
        if(isNull(userDto.getProducts())){
            throw new BadRequestException();
        } else if (userDto.getProducts().isEmpty()) {
            throw new BadRequestException();
        }
        for(var product : userDto.getProducts()){
            if(isNull(product.getId()) || isNull(product.getQuantity())){
                throw new BadRequestException();
            }
            if(product.getQuantity() < 0 || product.getId() < 0){
                throw new BadRequestException();
            }
        }
        if(isNull(userDto.getBalanceDebitCard())){
            throw new BadRequestException();
        }
    }
}
