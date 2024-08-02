package ru.clevertec.check.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    @Modifying
    @Query("UPDATE ProductEntity AS p SET p.description = ?1, p.price = ?2, p.quantity = ?3, p.isWholesale = ?4 WHERE p.id = ?5")
    void fullUpdate(String description, Float price, Integer quantity, Boolean isWholeSale, Integer id);

    @Modifying
    @Query("UPDATE ProductEntity AS p SET p.quantity = ?1 WHERE p.id = ?2")
    void decreaseAmount(Integer quantity, Integer id);
}
