package com.restaurant.MiniProjectPhase2.service;

import com.restaurant.MiniProjectPhase2.model.TableBooking;
import java.util.List;

public interface BookingService {
    TableBooking createBooking(TableBooking booking);
    TableBooking getBookingById(Long id);
    List<TableBooking> getAllBookings();
    TableBooking updateBooking(Long id, TableBooking bookingDetails);
    void deleteBooking(Long id);
}
