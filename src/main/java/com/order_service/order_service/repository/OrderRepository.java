package com.order_service.order_service.repository;

import com.order_service.order_service.dto.CustomerOrderDTO;
import com.order_service.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

//    @Query(value = "SELECT new com.example.CustomerOrderDTO(o.nameCustomer, COUNT(o)) " +
//            "FROM TrxOrder o " +
//            "WHERE o.createdAt >= :startDate AND o.createdAt < :endDate " +
//            "GROUP BY o.nameCustomer",nativeQuery = true)
//    List<CustomerOrderDTO> findCustomerOrders(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

//    @Query(value = "SELECT o.name_customer AS nameCustomer, COUNT(o) AS totalOrder " +
//            "FROM trx_order o " +
//            "WHERE o.created_at >= :startDate AND o.created_at < :endDate " +
//            "GROUP BY o.name_customer", nativeQuery = true)
//    List<CustomerOrderDTO> findCustomerOrders(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT o.name_customer, COUNT(o) " +
            "FROM trx_order o " +
            "WHERE o.created_at >= :startDate AND o.created_at < :endDate " +
            "GROUP BY o.name_customer", nativeQuery = true)
    List<Object[]> findCustomerOrders(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
