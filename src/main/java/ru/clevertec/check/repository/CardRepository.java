package ru.clevertec.check.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.entity.DiscountCardEntity;
import ru.clevertec.check.dto.CardDto;
import ru.clevertec.check.exceptions.ResourceNotFoundException;
import ru.clevertec.check.mapper.row.CardRowMapper;


import java.util.Optional;

import static ru.clevertec.check.utils.SQLCommands.*;

@Repository
@RequiredArgsConstructor
public class CardRepository implements AbstractRepository<DiscountCardEntity, CardDto> {
    private final JdbcTemplate jdbcTemplate;
    private final CardRowMapper cardMapper;

    public DiscountCardEntity getByCardNumber(Integer cardNumber) {
        // todo check null
       return jdbcTemplate.queryForObject(DISCOUNT_BY_NUMBER, cardMapper, cardNumber);
    }

    @Override
    public DiscountCardEntity getById(Integer id) throws ResourceNotFoundException {
        return Optional.ofNullable(jdbcTemplate.queryForObject(DISCOUNT_CARD_BY_ID, cardMapper, id))
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void save(CardDto cardDto) {
        jdbcTemplate.update(SAVE_DISCOUNT_CARD, new Object[]{cardDto.getDiscountCard(), cardDto.getDiscountAmount()});
    }

    @Override
    public  void deleteById(Integer id) {
        jdbcTemplate.update(DELETE_DISCOUNT_CAR_BY_ID, id);
    }

    @Override
    public void update(CardDto cardDto, Integer id) {
        jdbcTemplate.update(FULL_UPDATE_DISCOUNT_CARD, new Object[]{cardDto.getDiscountCard(), cardDto.getDiscountAmount(), id});
    }
}
