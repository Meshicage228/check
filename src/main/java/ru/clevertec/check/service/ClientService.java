package ru.clevertec.check.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.clevertec.check.domain.CurrentClient;
import ru.clevertec.check.domain.InputStringDetails;

@AllArgsConstructor
@NoArgsConstructor
public class ClientService {
    public ProductService productService;
    public CardService cardService;

    public CurrentClient formClient(InputStringDetails inputStringDetails) {
        return CurrentClient.builder()
                .basket(productService.formCart(inputStringDetails.getExtractedPairs()))
                .discountDebitCard(cardService.formCard(inputStringDetails.getCardNumber()))
                .balanceDebitCard(inputStringDetails.getCardBalance())
                .build();
    }
}
