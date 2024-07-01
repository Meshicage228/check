package ru.clevertec.check.service;

import ru.clevertec.check.domain.InputStringDetails;

import java.util.ArrayList;
import java.util.List;

public class StringInputService {

    public boolean isValid(String[] args) {
        String combinedString = String.join(" ", args);

        return combinedString.matches(".*(\\d+-\\d+).*") &&
                combinedString.matches(".*discountCard=\\d{4}.*") &&
                combinedString.contains("balanceDebitCard=");
    }

    public InputStringDetails formStringDetails(String[] args){
        List<Integer> extractedNumbers = new ArrayList<>();
        String cardNumber = "";
        Float cardBalance = 0f;

        for (var item : args) {
            if (item.contains("-")) {
                String[] parts = item.split("-");
                for (String part : parts) {
                    extractedNumbers.add(Integer.parseInt(part));
                }
            }
            else if (item.startsWith("discountCard=")) {
                cardNumber = item.substring("discountCard=".length());
            } else if (item.startsWith("balanceDebitCard=")) {
                cardBalance = Float.parseFloat(item.substring("balanceDebitCard=".length()));
            }
        }

        InputStringDetails build = InputStringDetails.builder()
                .cardNumber(cardNumber)
                .extractedPairs(extractedNumbers)
                .cardBalance(cardBalance)
                .build();
        return build;
    }
}
