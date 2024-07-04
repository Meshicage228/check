package ru.clevertec.check.repository;

import ru.clevertec.check.config.DataBaseConfig;
import ru.clevertec.check.domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.clevertec.check.utils.SQLCommands.PRODUCT_BY_ID;

public class ProductRepository {
    public Product getById (Integer id){
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PRODUCT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() ? Product.builder()
                    .id(resultSet.getInt("id"))
                    .description(resultSet.getString("description"))
                    .price(resultSet.getFloat("price"))
                    .quantity(resultSet.getInt("quantity_in_stock"))
                    .isWholeSale(resultSet.getBoolean("wholesale_product"))
                    .build() : null;
        } catch (SQLException e) {
            throw  new RuntimeException("Error while finding product: " + id, e);
        }
    }
}
