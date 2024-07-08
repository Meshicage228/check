package ru.clevertec.check.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.exceptions.ResourceNotFoundException;
import ru.clevertec.check.mapper.ProductMapper;
import ru.clevertec.check.repository.ProductRepository;
import ru.clevertec.check.service.ProductService;

import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @NonNull
    private ProductRepository productRepository;
    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Override
    public ArrayList<ProductDto> formCart(ArrayList<ProductDto> productDtos) throws BadRequestException, ResourceNotFoundException {
        HashMap<Integer, Integer> productsMap = new HashMap<>();
        ArrayList<ProductDto> basket = new ArrayList<>();

        for (ProductDto product : productDtos) {
            productsMap.merge(product.getId(), product.getQuantity(), Integer::sum);
        }
        for (var pair : productsMap.entrySet()) {
            Integer keyValue = pair.getKey();
            Integer productCount = pair.getValue();

            ProductDto product = productMapper.toDto(productRepository.getById(keyValue));

            if (nonNull(product)) {
                if (product.getQuantity() < productCount) {
                    throw new BadRequestException();
                }
                product.setPurchaseQuantity(productCount);
                basket.add(product);
            } else {
                throw new BadRequestException();
            }
        }

        return basket;
    }

    @Override
    public ProductDto getById(Integer id) throws ResourceNotFoundException {
        return productMapper.toDto(productRepository.getById(id));
    }

    @Override
    public void save(ProductDto workoutDto) {
        productRepository.save(workoutDto);
    }

    @Override
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public void fullUpdateProduct(ProductDto workoutDto, Integer id) {
        productRepository.update(workoutDto, id);
    }

    @Override
    public void decreaseProductAmount(ArrayList<ProductDto> basket) throws ResourceNotFoundException {
        productRepository.decreaseAmount(basket);
    }
}
