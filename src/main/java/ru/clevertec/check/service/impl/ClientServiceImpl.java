package ru.clevertec.check.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.clevertec.check.dto.CardDto;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.dto.UserDto;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.exceptions.NotEnoughMoneyException;
import ru.clevertec.check.exceptions.ResourceNotFoundException;
import ru.clevertec.check.service.*;

import java.io.File;
import java.util.ArrayList;

import static java.util.Objects.nonNull;

@AllArgsConstructor
@NoArgsConstructor
public class ClientServiceImpl implements ClientService {
    public ProductService productService;
    public CardService cardService;
    public FilePrintService fileService;

    @Override
    public UserDto formClient(UserDto userDto) throws BadRequestException, ResourceNotFoundException {
        return UserDto.builder()
                .products(productService.formCart(userDto.getProducts()))
                .cardDto(cardService.formCard(userDto.getDiscountCard()))
                .balanceDebitCard(userDto.getBalanceDebitCard())
                .build();
    }

    @Override
    public File formTotalBill(UserDto userDto) throws NotEnoughMoneyException, ResourceNotFoundException {
        ArrayList<ProductDto> basket = userDto.getProducts();
        CardDto discountDebitCard = userDto.getCardDto();

        Float totalWithNoDiscount = 0f;
        Float totalDiscount = 0f;

        for (var product : basket) {
            Float productTotal = roundNumber(product.getPrice() * product.getPurchaseQuantity());
            Float discountRate = product.getIsWholesale() && product.getPurchaseQuantity() >= 5 ? 10 : (nonNull(discountDebitCard) ? discountDebitCard.getDiscountAmount() : 0f);
            Float discountMoneyAmount = roundNumber(productTotal * discountRate / 100);

            product.setIndividualDiscount(discountMoneyAmount);
            product.setFullCost(productTotal);

            totalWithNoDiscount += productTotal;
            totalDiscount += discountMoneyAmount;
        }

        Float totalWithDiscount = totalWithNoDiscount - totalDiscount;

        if (userDto.getBalanceDebitCard() < totalWithDiscount) {
            throw new NotEnoughMoneyException();
        }

        productService.decreaseProductAmount(basket);

        return fileService.createBillFile(userDto, roundNumber(totalWithNoDiscount), roundNumber(totalDiscount), roundNumber(totalWithDiscount));
    }

    private Float roundNumber(Float number) {
        return (Math.round(number * 100.0f) / 100.0f);
    }
}
