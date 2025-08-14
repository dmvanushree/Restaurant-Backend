package com.restaurant.MiniProjectPhase2.repository;
import com.restaurant.MiniProjectPhase2.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByTableBookingId(Long bookingId);
    List<Order> findByStatus(com.restaurant.MiniProjectPhase2.enums.OrderStatus status);
}