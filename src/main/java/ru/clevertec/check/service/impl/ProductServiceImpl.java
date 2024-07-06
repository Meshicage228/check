package ru.clevertec.check.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import ru.clevertec.check.domain.Product;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.exceptions.InternalServerError;
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
    public ArrayList<Product> formCart(HashMap<Integer, Integer> pairs) {
        ArrayList<Product> totalProducts = new ArrayList<>();
        for (var pair : pairs.entrySet()) {
            Integer keyValue = pair.getKey();
            Integer productCount = pair.getValue();

            Product product = productRepository.getById(keyValue);

            if (nonNull(product)) {
                if (product.getQuantity() < productCount) {
                    throw new InternalServerError();
                }
                product.setPurchaseQuantity(productCount);
                totalProducts.add(product);
            } else {
                throw new InternalServerError();
            }
        }

        return totalProducts;
    }

    @Override
    public ProductDto getById(Integer id) {
        return productMapper.toDto(productRepository.getById(id));
    }
}
