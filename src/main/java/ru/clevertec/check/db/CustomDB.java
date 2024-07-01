package ru.clevertec.check.db;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.clevertec.check.domain.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import static java.util.Objects.nonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomDB {
    public static HashMap<Integer, Product> products = new HashMap<>();
    public static HashMap<String, Integer> discountCards = new HashMap<>();
    private static String line = "";
    private static final String SPLITBY = ";";

    static {
        readAllProducts();
        readAllDiscountCards();
    }

    private static void readAllProducts(){
        try (BufferedReader br = new BufferedReader(new FileReader("./src/main/resources/products.csv"))){
            br.readLine();
            while (nonNull(line = br.readLine())) {
                String[] resultLine = line.split(SPLITBY);
                Product product = Product.builder()
                        .description(resultLine[1])
                        .price(Float.valueOf(resultLine[2]))
                        .quantity(Integer.valueOf(resultLine[3]))
                        .isWholeSale(Boolean.valueOf(resultLine[4]))
                        .build();
                products.put(Integer.valueOf(resultLine[0]), product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void readAllDiscountCards(){
        try (BufferedReader br = new BufferedReader(new FileReader("./src/main/resources/discountCards.csv"))){
            br.readLine();
            while (nonNull(line = br.readLine())) {
                String[] resultLine = line.split(SPLITBY);
                discountCards.put(resultLine[1], Integer.valueOf(resultLine[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
