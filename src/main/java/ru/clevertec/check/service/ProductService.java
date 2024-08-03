package ru.clevertec.check.service;

import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.exceptions.BadRequestException;

import java.util.ArrayList;

public interface ProductService {
    ArrayList<ProductDto> formCart(ArrayList<ProductDto> productDtos) throws BadRequestException;

    ProductDto getById(Integer id);

    ProductDto save(ProductDto workoutDto);

    void deleteById(Integer id);

    void fullUpdateProduct(ProductDto workoutDto, Integer id);

    void decreaseProductAmount(ArrayList<ProductDto> basket);
}
