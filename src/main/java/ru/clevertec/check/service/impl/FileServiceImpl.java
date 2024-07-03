package ru.clevertec.check.service.impl;

import ru.clevertec.check.domain.CurrentClient;
import ru.clevertec.check.domain.DiscountCard;
import ru.clevertec.check.domain.Product;
import ru.clevertec.check.service.FileService;

import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import static java.util.Objects.nonNull;

public class FileServiceImpl implements FileService {
    public void createBillFile(CurrentClient currentClient, Float... arr) {
        DiscountCard discountDebitCard = currentClient.getDiscountDebitCard();
        StringBuilder stringBuilder = new StringBuilder();
        Collections.sort(currentClient.getBasket(), Comparator.comparing(Product::getDescription));

        stringBuilder.append("Date;").append("Time \n");
        stringBuilder.append(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))).append(";").append(LocalTime.now().format(DateTimeFormatter.ofPattern("HH.mm.ss"))).append("\n");

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
            System.out.println("Exception while creating result.csv file" + ex.getMessage());
        }
    }

    private String convertNumber(Float number) {
        return String.format(Locale.ENGLISH, "%.2f$", number);
    }
}
