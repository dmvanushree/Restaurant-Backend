package com.restaurant.MiniProjectPhase2.repository;

import com.restaurant.MiniProjectPhase2.model.TableBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<TableBooking, Long> {
}
