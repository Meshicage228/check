package ru.clevertec.check.service;

import ru.clevertec.check.dto.CardDto;

public interface CardService {
    CardDto formCard(Integer cardNumber);

    CardDto save(CardDto workoutDto);

    void deleteById(Integer id);

    void fullUpdateCard(CardDto cardDto, Integer id);

    CardDto getById (Integer id);
}
