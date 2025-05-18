package com.ivanfrias.company.orders.dao.repositories;

import com.ivanfrias.company.orders.dao.entities.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<StateEntity, Long> {
}
