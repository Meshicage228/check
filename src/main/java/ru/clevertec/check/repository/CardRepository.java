package ru.clevertec.check.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.entity.DiscountCardEntity;

@Repository
public interface CardRepository extends JpaRepository<DiscountCardEntity, Integer> {

    DiscountCardEntity getByNumber(Integer cardNumber);

    @Modifying
    @Query("UPDATE DiscountCardEntity d SET d.number = ?1, d.discountAmount = ?2 WHERE d.id = ?3")
    void updateCard(Integer number, Integer amount, Integer id);
}
