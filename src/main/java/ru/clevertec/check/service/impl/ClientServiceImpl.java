package ru.clevertec.check.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.clevertec.check.domain.CurrentClient;
import ru.clevertec.check.domain.InputStringDetails;
import ru.clevertec.check.service.CardService;
import ru.clevertec.check.service.ClientService;
import ru.clevertec.check.service.ProductService;

@AllArgsConstructor
@NoArgsConstructor
public class ClientServiceImpl implements ClientService {
    public ProductService productService;
    public CardService cardService;

    @Override
    public CurrentClient formClient(InputStringDetails inputStringDetails) {
        return CurrentClient.builder()
                .basket(productService.formCart(inputStringDetails.getExtractedPairs()))
                .discountDebitCard(cardService.formCard(inputStringDetails.getCardNumber()))
                .balanceDebitCard(inputStringDetails.getCardBalance())
                .build();
    }
}
