package ru.clevertec.check.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.clevertec.check.BillApplication;
import ru.clevertec.check.service.*;
import ru.clevertec.check.service.impl.*;
import ru.clevertec.check.utils.StringInputUtil;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceFactory {

    public static StringInputUtil createStringInputUtil() {
        return new StringInputUtil();
    }

    public static ProductService createProductService() {
        return new ProductServiceImpl();
    }

    public static CardService createCardService() {
        return new CardServiceImpl();
    }

    public static ClientService createClientService() {
        ProductService productService = createProductService();
        CardService cardService = createCardService();
        return new ClientServiceImpl(productService, cardService);
    }

    public static FileService createFileService() {
        return new FileServiceImpl();
    }

    public static BillService createBillService() {
        FileService fileService = createFileService();
        return new BillServiceImpl(fileService);
    }

    public static BillApplication createBillApplication() {
        StringInputUtil stringInputUtil = createStringInputUtil();
        ClientService clientService = createClientService();
        BillService billService = createBillService();
        return new BillApplication(stringInputUtil, clientService, billService);
    }
}
