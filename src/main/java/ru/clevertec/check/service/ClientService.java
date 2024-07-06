package ru.clevertec.check.service;

import ru.clevertec.check.domain.CurrentClient;
import ru.clevertec.check.domain.InputStringDetails;

public interface ClientService {
    CurrentClient formClient(InputStringDetails inputStringDetails);
}
