package ru.clevertec.check.repository;

import ru.clevertec.check.config.DataBaseConfig;
import ru.clevertec.check.domain.DiscountCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.clevertec.check.utils.SQLCommands.DISCOUNT_BY_NUMBER;

public class CardRepository {

    public DiscountCard getByCardNumber(Integer cardNumber){
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DISCOUNT_BY_NUMBER)) {
            preparedStatement.setInt(1, cardNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() ? DiscountCard.builder()
                    .id(resultSet.getInt("id"))
                    .number(resultSet.getInt("number"))
                    .discountAmount(resultSet.getInt("amount"))
                    .build() : null;
        } catch (SQLException e) {
           throw  new RuntimeException("Error while finding card: " + cardNumber, e);
        }
    }
}
