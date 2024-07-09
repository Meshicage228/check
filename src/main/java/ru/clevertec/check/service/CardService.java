package ru.clevertec.check.service;

import ru.clevertec.check.dto.CardDto;
import ru.clevertec.check.exceptions.ResourceNotFoundException;

public interface CardService {
    CardDto formCard(Integer cardNumber);

    void save(CardDto workoutDto);

    void deleteById(Integer id);

    void fullUpdateCard(CardDto cardDto, Integer id);

    CardDto getById (Integer id) throws ResourceNotFoundException;
}
