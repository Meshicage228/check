package ru.clevertec.check.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.check.dto.CardDto;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.dto.UserDto;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.exceptions.NotEnoughMoneyException;
import ru.clevertec.check.service.*;

import java.io.File;
import java.util.ArrayList;

import static java.util.Objects.nonNull;

@AllArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {
    public final ProductService productService;
    public final CardService cardService;
    public final FilePrintService fileService;

    @Override
    public UserDto formClient(UserDto userDto) throws BadRequestException {
        return UserDto.builder()
                .products(productService.formCart(userDto.getProducts()))
                .cardDto(cardService.formCard(userDto.getDiscountCard()))
                .balanceDebitCard(userDto.getBalanceDebitCard())
                .build();
    }

    @Override
    public File formTotalBill(UserDto userDto) throws NotEnoughMoneyException {
        ArrayList<ProductDto> basket = userDto.getProducts();
        CardDto discountDebitCard = userDto.getCardDto();

        Float totalWithNoDiscount = basket.stream()
                .map(product -> roundNumber(product.getPrice() * product.getPurchaseQuantity()))
                .reduce(0f, Float::sum);

        Float totalDiscount = basket.stream()
                .map(product -> {
                    Float discountRate = product.getIsWholesale() && product.getPurchaseQuantity() >= 5 ? 10 : (nonNull(discountDebitCard) ? discountDebitCard.getDiscountAmount() : 0f);
                    Float discountMoneyAmount = roundNumber(product.getPrice() * product.getPurchaseQuantity() * discountRate / 100);
                    product.setIndividualDiscount(discountMoneyAmount);
                    product.setFullCost(roundNumber(product.getPrice() * product.getPurchaseQuantity()));
                    return discountMoneyAmount;
                })
                .reduce(0f, Float::sum);

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
