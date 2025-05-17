package com.ivanfrias.company.products.dao.repositories;

import com.ivanfrias.company.products.dao.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query("SELECT p FROM ProductEntity p " +
            "WHERE (:productName IS NULL OR p.productName = :productName) " +
            "AND (:categoryName IS NULL OR p.category.categoryName = :categoryName) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:companyId IS NULL OR p.company.id = :companyId)")
    List<ProductEntity> getProductsFilter(@Param("productName") String productName,
                                          @Param("categoryName") String categoryName,
                                          @Param("minPrice") Double minPrice,
                                          @Param("maxPrice") Double maxPrice,
                                          @Param("companyId") Long companyId
    );

    @Query("SELECT p FROM ProductEntity p " +
            "WHERE (:productName IS NULL OR p.productName = :productName) " +
            "AND (:categoryName IS NULL OR p.category.categoryName = :categoryName) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) "
    )
    Page<ProductEntity> getPagedProductsFilter(@Param("productName") String productName,
                                               @Param("categoryName") String categoryName,
                                               @Param("minPrice") Double minPrice,
                                               @Param("maxPrice") Double maxPrice,
                                               Pageable pageable
    );
}
