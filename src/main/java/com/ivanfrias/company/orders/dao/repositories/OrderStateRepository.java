package com.ivanfrias.company.orders.dao.repositories;

import com.ivanfrias.company.orders.dao.entities.OrderStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStateRepository extends JpaRepository<OrderStateEntity, Long> {
}
