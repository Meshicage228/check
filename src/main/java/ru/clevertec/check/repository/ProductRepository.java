package ru.clevertec.check.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.entity.ProductEntity;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.exceptions.ResourceNotFoundException;
import ru.clevertec.check.mapper.row.ProductRowMapper;

import java.util.ArrayList;

import static java.util.Objects.isNull;
import static ru.clevertec.check.utils.SQLCommands.*;

@Repository
@RequiredArgsConstructor
public class ProductRepository implements AbstractRepository<ProductEntity, ProductDto> {
    private final JdbcTemplate jdbcTemplate;
    private final ProductRowMapper productMapper;

    @Override
    public ProductEntity getById(Integer id) throws ResourceNotFoundException {
        ProductEntity productEntity = jdbcTemplate.queryForObject(PRODUCT_BY_ID, productMapper, id);

        if (isNull(productEntity)) {
            throw new ResourceNotFoundException();
        }

        return productEntity;
    }

    @Override
    public void save(ProductDto workoutDto) {
        jdbcTemplate.update(SAVE_PRODUCT, new Object[]{workoutDto.getDescription(), workoutDto.getPrice(),
                workoutDto.getQuantity(), workoutDto.getIsWholesale()});
    }

    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update(DELETE_PRODUCT_BY_ID, id);
    }

    @Override
    public void update(ProductDto workoutDto, Integer id) {
        jdbcTemplate.update(FULL_PRODUCT_UPDATE, new Object[]{workoutDto.getDescription(), workoutDto.getPrice(),
                workoutDto.getQuantity(), workoutDto.getIsWholesale(), id});
    }

    public void decreaseAmount(ArrayList<ProductDto> basket) throws ResourceNotFoundException {
        for (var product : basket) {
            ProductEntity byId = getById(product.getId());
            Integer newQuantity = byId.getQuantity() - product.getPurchaseQuantity();
            jdbcTemplate.update(UPDATE_PRODUCT_QUANTITY, new Object[]{newQuantity, product.getId()});
        }
    }
}
