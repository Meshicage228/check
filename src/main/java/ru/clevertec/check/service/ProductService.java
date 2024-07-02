package ru.clevertec.check.service;

import lombok.AllArgsConstructor;
import ru.clevertec.check.db.CustomDB;
import ru.clevertec.check.domain.Product;

import java.util.ArrayList;
import java.util.HashMap;

@AllArgsConstructor
public class ProductService {
    public ArrayList<Product> formCart(HashMap<Integer, Integer> pairs) {
        ArrayList<Product> totalProducts = new ArrayList<>();
        for (var pair : pairs.entrySet()) {
            Integer keyValue = pair.getKey();
            Integer productCount = pair.getValue();

            if (CustomDB.products.containsKey(keyValue)){
                Product product = CustomDB.products.get(keyValue);
                if (product.getQuantity() < productCount){
                    throw new RuntimeException("NOT ENOUGH AT STOCK");
                }
                product.setPurchaseQuantity(productCount);
                totalProducts.add(product);
            } else {
                throw new RuntimeException("NO SUCH PRODUCT FOUND IN DB!");
            }
        }
        
        return totalProducts;
    }
}
