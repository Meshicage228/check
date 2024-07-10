package ru.clevertec.check.service.impl;

import org.springframework.stereotype.Service;
import ru.clevertec.check.dto.CardDto;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.dto.UserDto;
import ru.clevertec.check.service.FilePrintService;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static java.util.Objects.nonNull;

@Service
public class FilePrintServiceImpl implements FilePrintService {

    @Override
    public File createBillFile(UserDto userDto, Float... arr) {
        CardDto discountDebitCard = userDto.getCardDto();
        StringBuilder stringBuilder = new StringBuilder();
        Collections.sort(userDto.getProducts(), Comparator.comparing(ProductDto::getDescription));

        stringBuilder.append("Date;").append("Time \n");
        stringBuilder.append(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))).append(";").append(LocalTime.now().format(DateTimeFormatter.ofPattern("HH.mm.ss"))).append("\n \n");

        stringBuilder.append("QTY;").append("DESCRIPTION;").append("PRICE;").append("DISCOUNT;").append("TOTAL \n");

        for (var product : userDto.getProducts()) {
            stringBuilder.append(product.getPurchaseQuantity()).append(";")
                    .append(product.getDescription()).append(";")
                    .append(convertNumber(product.getPrice())).append(";")
                    .append(convertNumber(product.getIndividualDiscount())).append(";")
                    .append(convertNumber(product.getFullCost())).append("\n");
        }
        if (nonNull(discountDebitCard)) {
            stringBuilder.append("\n").append("DISCOUNT CARD;").append("DISCOUNT PERCENTAGE \n");
            stringBuilder.append(discountDebitCard.getDiscountCard()).append(";").append(discountDebitCard.getDiscountAmount()).append("% \n");
        }
        stringBuilder.append("\nTOTAL PRICE;").append("TOTAL DISCOUNT;").append("TOTAL WITH DISCOUNT \n");
        stringBuilder.append(convertNumber(arr[0])).append(";")
                .append(convertNumber(arr[1])).append(";")
                .append(convertNumber(arr[2])).append("\n");

        File billFile = new File("result.csv");

        try (FileWriter fileWriter = new FileWriter(billFile)) {
            fileWriter.write(stringBuilder.toString());
            printBillConsole(userDto.getProducts(), arr);
        } catch (Exception ex) {
            System.out.println("Exception while creating result.csv file: " + ex.getMessage());
        }

        return billFile;
    }

    @Override
    public void printBillConsole(List<ProductDto> productList, Float... arr) {
        System.out.println("********** TOTAL BILL **********");
        for (var product : productList) {
            System.out.printf("* %-20s *\n", "Description: " + product.getDescription());
            System.out.printf("* %-20s *\n", "Price: " + product.getPrice() + " $");
            System.out.printf("* %-20s *\n", "Quantity: " + product.getPurchaseQuantity());
            System.out.printf("* %-20s *\n", "Total discount: " + product.getIndividualDiscount() + " $");
            System.out.printf("* %-20s *\n", "Total cost: " + product.getFullCost() + " $");
            System.out.println("*********************************");
        }
        System.out.println("Total price : " + convertNumber(arr[0]));
        System.out.println("Total discount : " + convertNumber(arr[1]));
        System.out.println("Total with discount : " + convertNumber(arr[2]));
        System.out.println("*********************************");
    }

    private String convertNumber(Float number) {
        return String.format(Locale.ENGLISH, "%.2f$", number);
    }
}
