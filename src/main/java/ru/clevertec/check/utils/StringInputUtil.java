package ru.clevertec.check.utils;

import ru.clevertec.check.domain.InputStringDetails;

import java.util.HashMap;

public class StringInputUtil {

    public boolean isValid(String[] args) {
        String combinedString = String.join(" ", args);

        return combinedString.matches(".*(\\d+-\\d+).*") &&
                combinedString.contains("balanceDebitCard=");
    }

    public InputStringDetails formStringDetails(String[] args){
        HashMap<Integer, Integer> pairs = new HashMap<>();
        String cardNumber = "";
        Float cardBalance = 0f;

        for (var item : args) {
            if (item.contains("-") && !item.contains("=-")) {
                String[] parts = item.split("-");
                int key = Integer.parseInt(parts[0]);
                int value = Integer.parseInt(parts[1]);

                if(pairs.containsKey(key)){
                    pairs.put(key, pairs.get(key) + value);
                } else {
                    pairs.put(key, value);
                }
            }
            else if (item.startsWith("discountCard=")) {
                cardNumber = item.substring("discountCard=".length());
            } else if (item.startsWith("balanceDebitCard=")) {
                cardBalance = Float.parseFloat(item.substring("balanceDebitCard=".length()));
            }
        }

        return InputStringDetails.builder()
                .cardNumber(cardNumber)
                .extractedPairs(pairs)
                .cardBalance(cardBalance)
                .build();
    }
}
