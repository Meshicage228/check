package ru.clevertec.check.domain;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class InputStringDetails {
    private List<Integer> extractedPairs;
    private Float cardBalance;
    private String cardNumber;
}
