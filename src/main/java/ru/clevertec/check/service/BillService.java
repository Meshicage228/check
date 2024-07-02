package ru.clevertec.check.service;

import ru.clevertec.check.domain.CurrentClient;
import ru.clevertec.check.domain.DiscountCard;
import ru.clevertec.check.domain.Product;

import java.io.FileWriter;
import java.util.*;


import static java.util.Objects.nonNull;

public class BillService {
    public void formTotalBill(CurrentClient currentClient) {
        ArrayList<Product> basket = currentClient.getBasket();
        DiscountCard discountDebitCard = currentClient.getDiscountDebitCard();

        Float totalWithNoDiscount = 0f;
        Float totalDiscount = 0f;

        for (Product product : basket) {
            Float productTotal = roundNumber(product.getPrice() * product.getPurchaseQuantity());
            Float discountRate = product.getIsWholeSale() && product.getPurchaseQuantity() >= 5 ? 10 : (nonNull(discountDebitCard) ? discountDebitCard.getDiscountAmount() : 0f);
            Float discountMoneyAmount = roundNumber(productTotal * discountRate / 100);

            product.setIndividualDiscount(discountMoneyAmount);
            product.setFullCost(productTotal);

            printProductDetails(product, productTotal, discountMoneyAmount);

            totalWithNoDiscount += productTotal;
            totalDiscount += discountMoneyAmount;
        }

        Float totalWithDiscount = totalWithNoDiscount - totalDiscount;

        if (currentClient.getBalanceDebitCard() < totalWithDiscount) {
            throw new RuntimeException("NO SUCH MONEY!");
        }

        System.out.println("Total price : " + roundNumber(totalWithNoDiscount));
        System.out.println("Total discount : " + roundNumber(totalDiscount));
        System.out.println("Total with discount : " + roundNumber(totalWithDiscount));
        System.out.println("SUCCESS!");
        createFile(currentClient, totalWithNoDiscount, totalDiscount, totalWithDiscount);
    }

    private void createFile(CurrentClient currentClient, Float... arr) {
        DiscountCard discountDebitCard = currentClient.getDiscountDebitCard();
        StringBuilder stringBuilder = new StringBuilder();
        Collections.sort(currentClient.getBasket(), Comparator.comparing(Product::getDescription));
        stringBuilder.append("Date;").append("Time \n").append("Some Date;").append("some Time \n \n");
        stringBuilder.append("QTY;").append("DESCRIPTION;").append("PRICE;").append("DISCOUNT;").append("TOTAL \n");

        for (var product : currentClient.getBasket()) {
            stringBuilder.append(product.getPurchaseQuantity()).append(";")
                    .append(product.getDescription()).append(";")
                    .append(convertNumber(product.getPrice())).append(";")
                    .append(convertNumber(product.getIndividualDiscount())).append(";")
                    .append(convertNumber(product.getFullCost())).append("\n");
        }
        if (nonNull(discountDebitCard)) {
            stringBuilder.append("\n").append("DISCOUNT CARD;").append("DISCOUNT PERCENTAGE \n");
            stringBuilder.append(discountDebitCard.getNumber()).append(";").append(discountDebitCard.getDiscountAmount()).append("% \n");
        }
        stringBuilder.append("\nTOTAL PRICE;").append("TOTAL DISCOUNT;").append("TOTAL WITH DISCOUNT \n");
        stringBuilder.append(convertNumber(arr[0])).append(";")
                .append(convertNumber(arr[1])).append(";")
                .append(convertNumber(arr[2])).append("\n");
        try (FileWriter fileWriter = new FileWriter("src/result.csv")) {
            fileWriter.write(stringBuilder.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String convertNumber(Float number) {
        return String.format(Locale.ENGLISH, "%.2f$", number);
    }

    private Float roundNumber(Float number) {
        return (Math.round(number * 100.0f) / 100.0f);
    }

    private void printProductDetails(Product product, Float productTotal, Float discountMoneyAmount) {
        System.out.println("Quantity : " + product.getPurchaseQuantity());
        System.out.println("Description : " + product.getDescription());
        System.out.println("Price : " + product.getPrice());
        System.out.println("Total : " + productTotal);
        System.out.println("Discount : " + discountMoneyAmount);
    }
}
