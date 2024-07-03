package ru.clevertec.check.service;

import ru.clevertec.check.domain.CurrentClient;

public interface BillService {
    void formTotalBill(CurrentClient currentClient);
}
