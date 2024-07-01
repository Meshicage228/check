package ru.clevertec.check.service;

import lombok.AllArgsConstructor;
import ru.clevertec.check.db.CustomDB;
import ru.clevertec.check.domain.Product;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ProductService {
    public ArrayList<Product> formCart(List<Integer> pairs) {
        ArrayList<Product> totalProducts = new ArrayList<>();
        for (int i = 0; i < pairs.size(); i+=2) {
            Integer keyValue = pairs.get(i);
            Integer productCount = pairs.get(i+1);

            if (CustomDB.products.containsKey(pairs.get(i))){
                Product product = CustomDB.products.get(keyValue);
                product.setPurchaseQuantity(productCount);
                totalProducts.add(product);
            } else {
                throw new RuntimeException("NO SUCH PRODUCT FOUND IN DB!");
            }
        }
        
        return totalProducts;
    }
}
