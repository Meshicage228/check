package ru.clevertec.check.service.impl;

import lombok.AllArgsConstructor;
import ru.clevertec.check.db.CustomDB;
import ru.clevertec.check.domain.Product;
import ru.clevertec.check.exceptions.InternalServerError;
import ru.clevertec.check.service.ProductService;

import java.util.ArrayList;
import java.util.HashMap;

@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Override
    public ArrayList<Product> formCart(HashMap<Integer, Integer> pairs) {
        ArrayList<Product> totalProducts = new ArrayList<>();
        for (var pair : pairs.entrySet()) {
            Integer keyValue = pair.getKey();
            Integer productCount = pair.getValue();

            if (CustomDB.products.containsKey(keyValue)){
                Product product = CustomDB.products.get(keyValue);
                if (product.getQuantity() < productCount){
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
