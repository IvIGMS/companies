package com.ivanfrias.company.products.dao.repositories;

import com.ivanfrias.company.products.dao.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByCompanyId(Long companyId);
    CategoryEntity findByCompanyIdAndId(Long companyId, Long id);
}
