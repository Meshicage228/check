package ru.clevertec.check.service;

import ru.clevertec.check.domain.CurrentClient;
import ru.clevertec.check.domain.Product;

import java.util.List;

public interface FilePrintService {
    void createBillFile(CurrentClient currentClient, Float... arr);
    void printBillConsole(List<Product> productList, Float... arr);
}
