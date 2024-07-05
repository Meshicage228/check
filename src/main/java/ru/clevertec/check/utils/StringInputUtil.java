package ru.clevertec.check.utils;

import ru.clevertec.check.domain.InputStringDetails;
import ru.clevertec.check.exceptions.BadRequestException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StringInputUtil {

    public InputStringDetails formStringDetails(String[] args) {
        validateInputString(args);
        HashMap<Integer, Integer> pairs = new HashMap<>();
        Map<String, String> values = new HashMap<>();

        Stream.of(args).forEach(item -> {
            if (item.contains("-") && !item.contains("=-")) {
                String[] parts = item.split("-");
                int key = Integer.parseInt(parts[0]);
                int value = Integer.parseInt(parts[1]);
                pairs.merge(key, value, Integer::sum);
            } else {
                String[] keyValue = item.split("=", 2);
                if (keyValue.length == 2) {
                    values.put(keyValue[0], keyValue[1]);
                }
            }
        });

        return InputStringDetails.builder()
                .cardNumber(values.get("discountCard"))
                .extractedPairs(pairs)
                .cardBalance(Float.parseFloat(values.get("balanceDebitCard")))
                .password(values.get("datasource.password"))
                .url(values.get("datasource.url"))
                .username(values.get("datasource.username"))
                .build();
    }

    public void validateInputString(String[] args) {
        String fullString = String.join(" ", args);

        Pattern savePattern = Pattern.compile("saveToFile=(.*?\\.csv)");
        Matcher saveMatcher = savePattern.matcher(fullString);

        if (!(fullString.contains("datasource.url=") && fullString.contains("datasource.username=") || fullString.contains("datasource.password="))) {
            throw new BadRequestException();
        } else if (!saveMatcher.find()) {
            throw new BadRequestException();
        } else if (!(fullString.matches(".*(\\d+-\\d+).*") &&
                fullString.contains("balanceDebitCard="))) {
            throw new BadRequestException();
        }
    }
}
