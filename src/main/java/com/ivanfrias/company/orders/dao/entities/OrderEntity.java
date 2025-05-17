package com.ivanfrias.company.orders.dao.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ivanfrias.company.products.dao.entities.ProductEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String providerName;

    @Column(nullable = false)
    private Integer totalAmount;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private BigDecimal priceUnit;

    @Column(nullable = false)
    private boolean isDelivered = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @CreationTimestamp
    @Column(updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @PrePersist
    @PreUpdate
    private void calculatePriceUnit() {
        if (totalAmount != null && totalPrice != null && totalAmount != 0) {
            BigDecimal amountAsBigDecimal = BigDecimal.valueOf(totalAmount);
            this.priceUnit = totalPrice.divide(amountAsBigDecimal, 2, RoundingMode.HALF_UP);
        } else {
            this.priceUnit = BigDecimal.ZERO;
        }
    }
}
