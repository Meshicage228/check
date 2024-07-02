package ru.clevertec.check;

import ru.clevertec.check.factory.ServiceFactory;
import ru.clevertec.check.service.BillService;
import ru.clevertec.check.service.StringInputService;
import ru.clevertec.check.utils.FileService;

public class CheckRunner {
    public static void main(String[] args) {
        BillApplication billApplication = new BillApplication(new StringInputService(), ServiceFactory.buildClientService(), new BillService(new FileService()), new FileService());
        billApplication.start(args);
    }
}
