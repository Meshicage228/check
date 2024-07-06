package ru.clevertec.check.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.clevertec.check.BillApplication;
import ru.clevertec.check.repository.CardRepository;
import ru.clevertec.check.repository.ProductRepository;
import ru.clevertec.check.service.*;
import ru.clevertec.check.service.impl.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceFactory {
    public static ProductRepository createProductRepository(){
        return new ProductRepository();
    }

    public static CardRepository createCardRepository(){
        return new CardRepository();
    }

    public static ProductService createProductService() {
        return new ProductServiceImpl(createProductRepository());
    }

    public static CardService createCardService() {
        return new CardServiceImpl(createCardRepository());
    }

    public static ClientService createClientService() {
        ProductService productService = createProductService();
        CardService cardService = createCardService();
        return new ClientServiceImpl(productService, cardService);
    }

    public static FilePrintService createFileService() {
        return new FilePrintServiceImpl();
    }

    public static BillService createBillService() {
        FilePrintService fileService = createFileService();
        return new BillServiceImpl(fileService);
    }

    public static BillApplication createBillApplication() {
        ClientService clientService = createClientService();
        BillService billService = createBillService();
        return new BillApplication(clientService, billService);
    }
}
