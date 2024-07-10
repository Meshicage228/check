package ru.clevertec.check.mapper.row;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.check.entity.ProductEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductRowMapper implements RowMapper<ProductEntity> {
    @Override
    public ProductEntity mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return ProductEntity.builder()
                .id(resultSet.getInt("id"))
                .description(resultSet.getString("description"))
                .price(resultSet.getFloat("price"))
                .quantity(resultSet.getInt("quantity_in_stock"))
                .isWholesale(resultSet.getBoolean("wholesale_product"))
                .build();
    }
}
