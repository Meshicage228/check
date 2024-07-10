package ru.clevertec.check.mapper.row;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.check.entity.DiscountCardEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CardRowMapper implements RowMapper<DiscountCardEntity> {
    @Override
    public DiscountCardEntity mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return DiscountCardEntity.builder()
                .id(resultSet.getInt("id"))
                .number(resultSet.getInt("number"))
                .discountAmount(resultSet.getInt("amount")).build();
    }
}
