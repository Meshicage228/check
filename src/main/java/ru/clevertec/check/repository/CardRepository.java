package ru.clevertec.check.repository;

import ru.clevertec.check.config.DataBaseConfig;
import ru.clevertec.check.entity.DiscountCardEntity;
import ru.clevertec.check.dto.CardDto;
import ru.clevertec.check.exceptions.ResourceNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.clevertec.check.utils.SQLCommands.*;

public class CardRepository implements AbstractRepository<DiscountCardEntity, CardDto> {

    public DiscountCardEntity getByCardNumber(Integer cardNumber) {
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DISCOUNT_BY_NUMBER)) {
            preparedStatement.setInt(1, cardNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() ? DiscountCardEntity.builder()
                    .id(resultSet.getInt("id"))
                    .number(resultSet.getInt("number"))
                    .discountAmount(resultSet.getInt("amount"))
                    .build() : null;
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding card: " + cardNumber, e);
        }
    }

    @Override
    public DiscountCardEntity getById(Integer id) throws ResourceNotFoundException {
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DISCOUNT_CARD_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return DiscountCardEntity.builder()
                        .id(resultSet.getInt("id"))
                        .number(resultSet.getInt("number"))
                        .discountAmount(resultSet.getInt("amount"))
                        .build();
            } else {
                throw new ResourceNotFoundException();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while finding card by id: " + id, e);
        }
    }

    @Override
    public void save(CardDto cardDto) {
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_DISCOUNT_CARD)) {
            preparedStatement.setInt(1, cardDto.getDiscountCard());
            preparedStatement.setInt(2, cardDto.getDiscountAmount());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while saving discount card: " + cardDto.getDiscountCard(), e);
        }
    }

    @Override
    public  void deleteById(Integer id) {
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DISCOUNT_CAR_BY_ID)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting discount card: " + id, e);
        }
    }

    @Override
    public void update(CardDto cardDto, Integer id) {
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FULL_UPDATE_DISCOUNT_CARD)) {
            preparedStatement.setInt(1, cardDto.getDiscountCard());
            preparedStatement.setInt(2, cardDto.getDiscountAmount());
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating discount card: " + id, e);
        }
    }
}
