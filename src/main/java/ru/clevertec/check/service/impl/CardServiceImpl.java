package ru.clevertec.check.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import ru.clevertec.check.dto.CardDto;
import ru.clevertec.check.exceptions.ResourceNotFoundException;
import ru.clevertec.check.mapper.CardMapper;
import ru.clevertec.check.repository.CardRepository;
import ru.clevertec.check.service.CardService;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    @NonNull
    private CardRepository cardRepository;
    private CardMapper cardMapper = Mappers.getMapper(CardMapper.class);

    @Override
    public CardDto formCard(Integer cardNumber) {
        if(isNull(cardNumber)){
            return null;
        }
        CardDto byCardNumber = cardMapper.toDto(cardRepository.getByCardNumber(cardNumber));

        return nonNull(byCardNumber) ? byCardNumber :
                CardDto.builder()
                        .discountAmount(2)
                        .discountCard(cardNumber).build();
    }

    @Override
    public void save(CardDto workoutDto) {
        cardRepository.save(workoutDto);
    }

    @Override
    public void deleteById(Integer id) {
        cardRepository.deleteById(id);
    }

    @Override
    public void fullUpdateCard(CardDto cardDto, Integer id) {
        cardRepository.update(cardDto, id);
    }

    @Override
    public CardDto getById(Integer id) throws ResourceNotFoundException {
        return cardMapper.toDto(cardRepository.getById(id));
    }
}
