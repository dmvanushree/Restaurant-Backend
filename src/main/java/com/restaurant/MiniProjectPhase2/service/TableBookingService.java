package com.restaurant.MiniProjectPhase2.service;

import com.restaurant.MiniProjectPhase2.model.TableBooking;
import java.util.List;

public interface TableBookingService {
    TableBooking create(TableBooking booking);
    List<TableBooking> findAll();
    TableBooking findById(Long id);
    TableBooking update(Long id, TableBooking booking);
    void delete(Long id);

    Object listBookings();

    Object createBooking(TableBooking any);
}
