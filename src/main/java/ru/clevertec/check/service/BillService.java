package ru.clevertec.check.service;

import ru.clevertec.check.dto.UserDto;
import ru.clevertec.check.exceptions.NotEnoughMoneyException;

public interface BillService {
    void formTotalBill(UserDto userDto) throws NotEnoughMoneyException;
}
