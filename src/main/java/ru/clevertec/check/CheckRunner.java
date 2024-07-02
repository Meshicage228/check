package ru.clevertec.check;

import ru.clevertec.check.factory.ServiceFactory;
import ru.clevertec.check.service.BillService;
import ru.clevertec.check.service.StringInputService;

public class CheckRunner {
    public static void main(String[] args) {
        BillApplication billApplication = new BillApplication(new StringInputService(), ServiceFactory.buildClientService(), new BillService());
        billApplication.start(args);
    }
}
