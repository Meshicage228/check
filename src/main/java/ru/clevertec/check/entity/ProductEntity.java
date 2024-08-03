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
@Table(name = "product")
@SequenceGenerator(name = "product_seq_gen",
        schema = "sequences",
        sequenceName = "product_seq",
        initialValue = 21, allocationSize = 1)

@Check(constraints = "price >= 0")
@Check(constraints = "quantity_in_stock >= 0")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_gen")
    private Integer id;
    private String description;
    @Column(columnDefinition="Decimal(10,2)")
    private Float price;
    @Column(name = "quantity_in_stock")
    private Integer quantity;
    @Column(name = "wholesale_product")
    private Boolean isWholesale;
}
