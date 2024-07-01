package ru.clevertec.check.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.clevertec.check.service.CardService;
import ru.clevertec.check.service.ClientService;
import ru.clevertec.check.service.ProductService;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceFactory {
    public static ClientService buildClientService() {
        return new ClientService(new ProductService(), new CardService());
    }
}
