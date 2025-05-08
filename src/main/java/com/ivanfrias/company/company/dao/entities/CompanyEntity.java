package com.ivanfrias.company.company.dao.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ivanfrias.company.products.dao.entities.CategoryEntity;
import com.ivanfrias.company.products.dao.entities.ProductEntity;
import com.ivanfrias.company.security.dao.models.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private AddressEntity address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    @JsonManagedReference
    private List<ProductEntity> products;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    @JsonManagedReference
    private List<CategoryEntity> categories;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @CreationTimestamp
    @Column(updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;
}

