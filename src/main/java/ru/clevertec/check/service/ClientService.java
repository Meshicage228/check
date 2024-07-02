package ru.clevertec.check.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.clevertec.check.domain.CurrentClient;
import ru.clevertec.check.domain.InputStringDetails;
import ru.clevertec.check.exceptions.InternalServerError;

@AllArgsConstructor
@NoArgsConstructor
public class ClientService {
    public ProductService productService;
    public CardService cardService;

    public CurrentClient formClient(InputStringDetails inputStringDetails) throws InternalServerError {
        return CurrentClient.builder()
                .basket(productService.formCart(inputStringDetails.getExtractedPairs()))
                .discountDebitCard(cardService.formCard(inputStringDetails.getCardNumber()))
                .balanceDebitCard(inputStringDetails.getCardBalance())
                .build();
    }
}
