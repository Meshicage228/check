package ru.clevertec.check.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.check.dto.CardDto;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.exceptions.ResourceNotFoundException;
import ru.clevertec.check.service.impl.CardServiceImpl;
import ru.clevertec.check.utils.InputDataValidator;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Controller
@RequestMapping("/discountcards")
@RequiredArgsConstructor
public class DiscountCardController {
    private final CardServiceImpl cardService;
    private final InputDataValidator validator;

    @GetMapping
    public ResponseEntity<CardDto> getById(@RequestParam("id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(cardService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Void> saveCard(@RequestBody CardDto cardDto) throws BadRequestException {
        validator.validateDiscountCard(cardDto);
        cardService.save(cardDto);
        return ResponseEntity.status(CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> putUpdateCard(@RequestBody CardDto cardDto,
                                              @RequestParam("id") Integer id) throws BadRequestException, ResourceNotFoundException {
        CardDto byId = cardService.getById(id);
        validator.validateDiscountCard(cardDto);
        cardService.fullUpdateCard(cardDto, id);
        return ResponseEntity.status(CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCard(@RequestParam("id") Integer id) throws ResourceNotFoundException {
        CardDto byId = cardService.getById(id);
        cardService.deleteById(id);

        return ResponseEntity.status(CREATED).build();
    }
}
