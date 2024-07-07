package ru.clevertec.check.service;

import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.exceptions.ResourceNotFoundException;

import java.util.ArrayList;

public interface ProductService {
    ArrayList<ProductDto> formCart(ArrayList<ProductDto> productDtos) throws BadRequestException, ResourceNotFoundException;

    ProductDto getById(Integer id) throws ResourceNotFoundException;

    void save(ProductDto workoutDto);

    void deleteById(Integer id);

    void fullUpdateProduct(ProductDto workoutDto, Integer id);
}
