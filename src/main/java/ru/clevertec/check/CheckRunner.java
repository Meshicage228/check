package ru.clevertec.check;

import ru.clevertec.check.factory.ServiceFactory;

public class CheckRunner {
    public static void main(String[] args) {
        BillApplication billApplication = ServiceFactory.createBillApplication();
        billApplication.start(args);
    }
}
