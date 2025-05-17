package com.ivanfrias.company.orders.dao.repositories;

import com.ivanfrias.company.company.dao.entities.CompanyEntity;
import com.ivanfrias.company.orders.dao.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query(value = "SELECT o FROM " +
            " OrderEntity o " +
            " JOIN o.product p " +
            " JOIN p.company c " +
            " WHERE c.id = :companyId")
    List<OrderEntity> getOrdersByCompanyId(@Param("companyId") Long companyId);
}
