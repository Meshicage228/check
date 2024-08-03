package ru.clevertec.check.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder

@Entity
@Table(name = "discount_card")
@SequenceGenerator(name = "discount_seq_gen",
        schema = "sequences",
        sequenceName = "discount_seq",
        initialValue = 5, allocationSize = 1)

@Check(constraints = "amount > 0 AND amount <= 100")
public class DiscountCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discount_seq_gen")
    private Integer id;
    private Integer number;
    @Column(name = "amount")
    private Integer discountAmount;
}
