package ru.clevertec.check.service;

import lombok.AllArgsConstructor;
import ru.clevertec.check.domain.CurrentClient;
import ru.clevertec.check.domain.DiscountCard;
import ru.clevertec.check.domain.Product;
import ru.clevertec.check.exceptions.NotEnoughMoneyException;
import ru.clevertec.check.utils.ExceptionMessages;
import ru.clevertec.check.utils.FileService;
import java.util.*;


import static java.util.Objects.nonNull;

@AllArgsConstructor
public class BillService {
    private FileService fileService;

    public void formTotalBill(CurrentClient currentClient) throws NotEnoughMoneyException {
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
            throw new NotEnoughMoneyException(ExceptionMessages.NOT_ENOUGH_MONEY);
        }

        System.out.println("Total price : " + roundNumber(totalWithNoDiscount));
        System.out.println("Total discount : " + roundNumber(totalDiscount));
        System.out.println("Total with discount : " + roundNumber(totalWithDiscount));
        System.out.println("SUCCESS!");

        fileService.createBillFile(currentClient, totalWithNoDiscount, totalDiscount, totalWithDiscount);
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
