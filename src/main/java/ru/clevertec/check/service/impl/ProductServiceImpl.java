package ru.clevertec.check.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.entity.ProductEntity;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.mapper.ProductMapper;
import ru.clevertec.check.repository.ProductRepository;
import ru.clevertec.check.service.ProductService;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ArrayList<ProductDto> formCart(ArrayList<ProductDto> productDtos) throws BadRequestException {
        return productDtos.stream()
                .collect(Collectors.groupingBy(ProductDto::getId, Collectors.summingInt(ProductDto::getQuantity)))
                .entrySet()
                .stream()
                .map(entry -> {
                    Integer key = entry.getKey();
                    Integer value = entry.getValue();

                    return Optional.of(productRepository.getReferenceById(key))
                            .filter(productEntity -> productEntity.getQuantity() < value)
                            .map(productEntity -> {
                                ProductDto product = productMapper.toDto(productEntity);
                                product.setPurchaseQuantity(value);
                                return product;
                            })
                            .orElseThrow(BadRequestException::new);
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ProductDto getById(Integer id) {
        return productMapper.toDto(productRepository.getReferenceById(id));
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        ProductEntity save = productRepository.save(productMapper.toEntity(productDto));
        return productMapper.toDto(save);
    }

    @Override
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void fullUpdateProduct(ProductDto workoutDto, Integer id) {
        productRepository.fullUpdate(workoutDto.getDescription(), workoutDto.getPrice(),
                workoutDto.getQuantity(), workoutDto.getIsWholesale(), id);
    }

    @Override
    @Transactional
    public void decreaseProductAmount(ArrayList<ProductDto> basket) {
        basket.forEach(product -> {
            ProductDto byId = getById(product.getId());
            Integer newQuantity = byId.getQuantity() - product.getPurchaseQuantity();
            productRepository.decreaseAmount(newQuantity, product.getId());
        });
    }
}
