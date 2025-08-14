package com.restaurant.MiniProjectPhase2.repository;


import com.restaurant.MiniProjectPhase2.model.TableBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TableBookingRepository extends JpaRepository<TableBooking, Long> {
    boolean existsByTableNumberAndBookingTime(int tableNumber, java.time.LocalDateTime bookingTime);
    List<TableBooking> findByTableNumber(int tableNumber);
}
