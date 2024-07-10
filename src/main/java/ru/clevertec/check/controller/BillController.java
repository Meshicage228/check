package ru.clevertec.check.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.clevertec.check.dto.UserDto;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.exceptions.NotEnoughMoneyException;
import ru.clevertec.check.exceptions.ResourceNotFoundException;
import ru.clevertec.check.service.impl.ClientServiceImpl;
import ru.clevertec.check.utils.InputDataValidator;

import java.io.*;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;


@Controller
@RequestMapping("/check")
@RequiredArgsConstructor
public class BillController {
    private final ClientServiceImpl clientService;
    private final InputDataValidator validator;

    @PostMapping
    public ResponseEntity<Resource> createBill(@RequestBody UserDto userDto) throws NotEnoughMoneyException, ResourceNotFoundException, BadRequestException, IOException {
        validator.validateUserDto(userDto);
        UserDto createdUser = clientService.formClient(userDto);
        File file = clientService.formTotalBill(createdUser);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.status(OK)
                .contentType(APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);
    }

}
