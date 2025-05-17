package com.ivanfrias.company.orders.dao.repositories;

import com.ivanfrias.company.orders.dao.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
