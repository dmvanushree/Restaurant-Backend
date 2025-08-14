package com.restaurant.MiniProjectPhase2.service.impl;

import com.restaurant.MiniProjectPhase2.model.TableBooking;
import com.restaurant.MiniProjectPhase2.repository.BookingRepository;
import com.restaurant.MiniProjectPhase2.service.BookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public TableBooking createBooking(TableBooking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public TableBooking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id " + id));
    }

    @Override
    public List<TableBooking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public TableBooking updateBooking(Long id, TableBooking bookingDetails) {
        TableBooking booking = getBookingById(id);
        booking.setCustomerName(bookingDetails.getCustomerName());
        booking.setBookingTime(bookingDetails.getBookingTime());
        booking.setTableNumber(bookingDetails.getTableNumber());
        booking.setNumberOfGuests(bookingDetails.getNumberOfGuests());
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
