package ru.clevertec.check.service;

import ru.clevertec.check.domain.CurrentClient;

public interface FileService {
    void createBillFile(CurrentClient currentClient, Float... arr);
}
