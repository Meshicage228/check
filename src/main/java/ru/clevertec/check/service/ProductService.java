package ru.clevertec.check.service;

import lombok.AllArgsConstructor;
import ru.clevertec.check.db.CustomDB;
import ru.clevertec.check.domain.Product;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
public class ProductService {
    public HashMap<Integer, Product> formCart(List<Integer> pairs) {
        HashMap<Integer, Product> totalProducts = new HashMap<>();
        for (int i = 0; i < pairs.size(); i+=2) {
            Integer keyValue = pairs.get(i);
            Integer productCount = pairs.get(i+1);

            if (CustomDB.products.containsKey(pairs.get(i))){
                totalProducts.put(productCount, CustomDB.products.get(keyValue));
            } else {
                throw new RuntimeException("NO SUCH PRODUCT FOUND IN DB!");
            }
        }
        
        return totalProducts;
    }
}
