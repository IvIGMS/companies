package com.ivanfrias.company.products.dao.repositories;

import com.ivanfrias.company.products.dao.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
