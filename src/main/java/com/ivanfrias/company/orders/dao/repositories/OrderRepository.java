package com.ivanfrias.company.orders.dao.repositories;

import com.ivanfrias.company.orders.dao.entities.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "SELECT o FROM " +
            " OrderEntity o " +
            " JOIN o.product p " +
            " JOIN p.company c " +
            " JOIN o.orderStates os WITH os.current = true " +
            " WHERE c.id = :companyId " +
            " AND (:productName IS NULL OR p.productName = :productName) " +
            " AND (:providerName IS NULL OR o.providerName = :providerName) " +
            " AND (:minTotalPrice IS NULL OR o.totalPrice >= :minTotalPrice) " +
            " AND (:maxTotalPrice IS NULL OR o.totalPrice <= :maxTotalPrice) " +
            " AND (:idOrderState IS NULL OR os.state.id = :idOrderState) "

    )
    Page<OrderEntity> getPagedOrders(@Param("productName") String productName,
                                     @Param("providerName")String providerName,
                                     @Param("companyId")Long companyId,
                                     @Param("minTotalPrice")Double minTotalPrice,
                                     @Param("maxTotalPrice")Double maxTotalPrice,
                                     @Param("idOrderState")Long idOrderState,
                                     Pageable pageable
    );
}
