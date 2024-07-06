package ru.clevertec.check.service.impl;

import lombok.AllArgsConstructor;
import ru.clevertec.check.domain.CurrentClient;
import ru.clevertec.check.domain.DiscountCard;
import ru.clevertec.check.domain.Product;
import ru.clevertec.check.exceptions.NotEnoughMoneyException;
import ru.clevertec.check.service.BillService;
import ru.clevertec.check.service.FilePrintService;

import java.util.*;

import static java.util.Objects.nonNull;

@AllArgsConstructor
public class BillServiceImpl implements BillService {
    private FilePrintService fileService;

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

            totalWithNoDiscount += productTotal;
            totalDiscount += discountMoneyAmount;
        }

        Float totalWithDiscount = totalWithNoDiscount - totalDiscount;

        if (currentClient.getBalanceDebitCard() < totalWithDiscount) {
            throw new NotEnoughMoneyException();
        }

        fileService.createBillFile(currentClient, roundNumber(totalWithNoDiscount), roundNumber(totalDiscount), roundNumber(totalWithDiscount));
    }

    private Float roundNumber(Float number) {
        return (Math.round(number * 100.0f) / 100.0f);
    }
}
