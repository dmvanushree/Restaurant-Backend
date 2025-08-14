package com.restaurant.MiniProjectPhase2.service.impl;

import com.restaurant.MiniProjectPhase2.exception.ResourceNotFoundException;
import com.restaurant.MiniProjectPhase2.model.TableBooking;
import com.restaurant.MiniProjectPhase2.repository.TableBookingRepository;
import com.restaurant.MiniProjectPhase2.repository.UserRepository;
import com.restaurant.MiniProjectPhase2.service.TableBookingService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;

import java.util.List;


@Service
public class TableBookingServiceImpl implements TableBookingService {
    private static final Logger log = LoggerFactory.getLogger(TableBookingServiceImpl.class);

    private final TableBookingRepository bookingRepo;
    private final UserRepository userRepo;

    public TableBookingServiceImpl(TableBookingRepository bookingRepo, UserRepository userRepo) {
        this.bookingRepo = bookingRepo;
        this.userRepo = userRepo;
    }

    @Override
    public TableBooking create(TableBooking booking) {
        log.info("Creating booking for {}", booking.getCustomerName());

        if (bookingRepo.existsByTableNumberAndBookingTime(booking.getTableNumber(), booking.getBookingTime())) {
            throw new IllegalArgumentException("Slot already booked for this table/time");
        }

        return bookingRepo.save(booking);
    }

    @Override
    public List<TableBooking> findAll() {
        return bookingRepo.findAll();
    }

    @Override
    public TableBooking findById(Long id) {
        return bookingRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found: " + id));
    }

    @Override
    public TableBooking update(Long id, TableBooking payload) {
        TableBooking booking = findById(id);
        log.info("Updating booking {}", id);

        booking.setCustomerName(payload.getCustomerName());
        booking.setBookingTime(payload.getBookingTime());
        booking.setTableNumber(payload.getTableNumber());
        booking.setNumberOfGuests(payload.getNumberOfGuests());

        if (payload.getHandledBy() != null && payload.getHandledBy().getId() != null) {
            var user = userRepo.findById(payload.getHandledBy().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "User not found: " + payload.getHandledBy().getId()
                    ));
            booking.setHandledBy(user);
        }

        return bookingRepo.save(booking);
    }

    @Override
    public void delete(Long id) {
        log.warn("Deleting booking {}", id);
        bookingRepo.deleteById(id);
    }

    @Override
    public Object listBookings() {
        return null;
    }

    @Override
    public Object createBooking(TableBooking any) {
        return null;
    }
}
