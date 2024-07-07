package ru.clevertec.check.service;

import ru.clevertec.check.dto.UserDto;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.exceptions.NotEnoughMoneyException;
import ru.clevertec.check.exceptions.ResourceNotFoundException;

import java.io.File;

public interface ClientService {
    UserDto formClient(UserDto userDto) throws BadRequestException, ResourceNotFoundException;
    File formTotalBill(UserDto userDto) throws NotEnoughMoneyException;
}
