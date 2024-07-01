package ru.clevertec.check;

import ru.clevertec.check.factory.ServiceFactory;
import ru.clevertec.check.service.StringInputService;

public class CheckRunner {
    public static void main(String[] args) {
        BillApplication billApplication = new BillApplication(new StringInputService(), ServiceFactory.buildClientService());
        billApplication.start(args);
    }
}
