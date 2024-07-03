package ru.clevertec.check.utils;

import ru.clevertec.check.db.CustomDB;
import ru.clevertec.check.domain.InputStringDetails;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.service.impl.FilePrintServiceImpl;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringInputUtil {

    public void validateInputString(String[] args) {
        String combinedString = String.join(" ", args);

        Pattern savePattern = Pattern.compile("saveToFile=(.*?\\.csv)");
        Pattern pathPattern = Pattern.compile("pathToFile=(.*?\\.csv)");

        Matcher saveMatcher = savePattern.matcher(combinedString);
        Matcher pathMatcher = pathPattern.matcher(combinedString);

        if (!saveMatcher.find()) {
            throw new BadRequestException();
        } else if (!pathMatcher.find()) {
            throw new BadRequestException(saveMatcher.group(1));
        } else if (!(combinedString.matches(".*(\\d+-\\d+).*") &&
                combinedString.contains("balanceDebitCard="))) {
            throw new BadRequestException();
        }
        CustomDB.PRODUCT_PATH_FILE = pathMatcher.group(1);
        FilePrintServiceImpl.TOTAL_BILL_PATH = saveMatcher.group(1);
    }

    public InputStringDetails formStringDetails(String[] args) {
        HashMap<Integer, Integer> pairs = new HashMap<>();
        String cardNumber = "";
        Float cardBalance = 0f;

        for (var item : args) {
            if (item.contains("-") && !item.contains("=-")) {
                String[] parts = item.split("-");
                int key = Integer.parseInt(parts[0]);
                int value = Integer.parseInt(parts[1]);

                if (pairs.containsKey(key)) {
                    pairs.put(key, pairs.get(key) + value);
                } else {
                    pairs.put(key, value);
                }
            } else if (item.startsWith("discountCard=")) {
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
