package ru.clevertec.check.domain;

import lombok.*;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class InputStringDetails {
    private HashMap<Integer, Integer> extractedPairs;
    private Float cardBalance;
    private String cardNumber;
}
