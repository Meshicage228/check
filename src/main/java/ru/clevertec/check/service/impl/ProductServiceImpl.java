package ru.clevertec.check.service.impl;

import lombok.AllArgsConstructor;
import ru.clevertec.check.domain.Product;
import ru.clevertec.check.exceptions.InternalServerError;
import ru.clevertec.check.repository.ProductRepository;
import ru.clevertec.check.service.ProductService;

import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Objects.nonNull;

@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

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
}
