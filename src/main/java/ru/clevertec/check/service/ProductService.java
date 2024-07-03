package ru.clevertec.check.service;

import ru.clevertec.check.domain.Product;

import java.util.ArrayList;
import java.util.HashMap;

public interface ProductService {
    ArrayList<Product> formCart(HashMap<Integer, Integer> pairs);
}
