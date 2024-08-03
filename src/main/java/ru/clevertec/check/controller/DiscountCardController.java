package ru.clevertec.check.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.check.dto.CardDto;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.service.impl.CardServiceImpl;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Controller
@RequestMapping("/discountcards")
@RequiredArgsConstructor
public class DiscountCardController {
    private final CardServiceImpl cardService;

    @GetMapping
    public ResponseEntity<CardDto> getById(@RequestParam("id") Integer id) {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(cardService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CardDto> saveCard(@Valid @RequestBody CardDto cardDto,
                                         BindingResult bindingResult) throws BadRequestException {
        if(bindingResult.hasFieldErrors()){
            throw new BadRequestException();
        }
        CardDto saved = cardService.save(cardDto);
        return ResponseEntity.status(CREATED)
                .body(saved);
    }

    @PutMapping
    public ResponseEntity<Void> putUpdateCard(@Valid @RequestBody CardDto cardDto,
                                              BindingResult bindingResult,
                                              @RequestParam("id") Integer id) throws BadRequestException {
        if(bindingResult.hasFieldErrors()){
            throw new BadRequestException();
        }
        cardService.fullUpdateCard(cardDto, id);
        return ResponseEntity.status(ACCEPTED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCard(@RequestParam("id") Integer id) {
        cardService.deleteById(id);

        return ResponseEntity.status(CREATED).build();
    }
}
