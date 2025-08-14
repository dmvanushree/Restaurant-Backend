package com.restaurant.MiniProjectPhase2.controller;

import com.restaurant.MiniProjectPhase2.model.TableBooking;
import com.restaurant.MiniProjectPhase2.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public TableBooking createBooking(@RequestBody TableBooking booking) {
        return bookingService.createBooking(booking);
    }

    @GetMapping("/{id}")
    public TableBooking getBooking(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @GetMapping
    public List<TableBooking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PutMapping("/{id}")
    public TableBooking updateBooking(@PathVariable Long id, @RequestBody TableBooking bookingDetails) {
        return bookingService.updateBooking(id, bookingDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }

    // Additional methods for programmatic access
    public Object create(TableBooking booking) {
        return bookingService.createBooking(booking);
    }

    public TableBooking one(long id) {
        return bookingService.getBookingById(id);
    }

    public List<TableBooking> all() {
        return bookingService.getAllBookings();
    }

    public TableBooking update(long id, TableBooking booking) {
        return bookingService.updateBooking(id, booking);
    }

    public Object delete(long id) {
        bookingService.deleteBooking(id);
        return "Deleted booking with id " + id;
    }
}
