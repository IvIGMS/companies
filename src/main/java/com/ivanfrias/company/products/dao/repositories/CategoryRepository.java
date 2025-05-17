package com.ivanfrias.company.products.dao.repositories;

import com.ivanfrias.company.products.dao.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByCompanyId(Long companyId);
    Optional<CategoryEntity> findByCompanyIdAndId(Long companyId, Long id);
}
