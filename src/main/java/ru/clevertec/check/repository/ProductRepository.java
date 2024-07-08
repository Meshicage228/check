package ru.clevertec.check.repository;

import ru.clevertec.check.config.DataBaseConfig;
import ru.clevertec.check.entity.ProductEntity;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.exceptions.ResourceNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static ru.clevertec.check.utils.SQLCommands.*;

public class ProductRepository implements AbstractRepository<ProductEntity, ProductDto> {

    @Override
    public ProductEntity getById(Integer id) throws ResourceNotFoundException {
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PRODUCT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return ProductEntity.builder()
                        .id(resultSet.getInt("id"))
                        .description(resultSet.getString("description"))
                        .price(resultSet.getFloat("price"))
                        .quantity(resultSet.getInt("quantity_in_stock"))
                        .isWholesale(resultSet.getBoolean("wholesale_product"))
                        .build();
            } else {
                throw new ResourceNotFoundException();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while finding product: " + id, e);
        }
    }

    @Override
    public void save(ProductDto workoutDto) {
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_PRODUCT)) {
            preparedStatement.setString(1, workoutDto.getDescription());
            preparedStatement.setFloat(2, workoutDto.getPrice());
            preparedStatement.setInt(3, workoutDto.getQuantity());
            preparedStatement.setBoolean(4, workoutDto.getIsWholesale());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while saving product: " + workoutDto.getDescription(), e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_BY_ID)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting product: " + id, e);
        }
    }

    @Override
    public void update(ProductDto workoutDto, Integer id) {
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FULL_PRODUCT_UPDATE)) {
            preparedStatement.setString(1, workoutDto.getDescription());
            preparedStatement.setFloat(2, workoutDto.getPrice());
            preparedStatement.setInt(3, workoutDto.getQuantity());
            preparedStatement.setBoolean(4, workoutDto.getIsWholesale());
            preparedStatement.setInt(5, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting product: " + id, e);
        }
    }

    public void decreaseAmount(ArrayList<ProductDto> basket) throws ResourceNotFoundException {
        for(var product : basket) {
            ProductEntity byId = getById(product.getId());
            Integer newQuantity = byId.getQuantity() - product.getPurchaseQuantity();
            try (Connection connection = DataBaseConfig.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_QUANTITY)) {
                preparedStatement.setInt(1, newQuantity);
                preparedStatement.setInt(2, product.getId());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error while setting new quantity to the product: " + product.getDescription(), e);
            }
        }
    }
}
