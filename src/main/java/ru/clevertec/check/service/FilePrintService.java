package ru.clevertec.check.service;

import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.entity.ProductEntity;
import ru.clevertec.check.dto.UserDto;

import java.io.File;
import java.util.List;

public interface FilePrintService {
    File createBillFile(UserDto userDto, Float... arr);
    void printBillConsole(List<ProductDto> productList, Float... arr);
}
