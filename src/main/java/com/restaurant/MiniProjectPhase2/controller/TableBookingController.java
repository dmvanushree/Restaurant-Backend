package com.restaurant.MiniProjectPhase2.controller;

import com.restaurant.MiniProjectPhase2.dto.BookingRequest;
import com.restaurant.MiniProjectPhase2.exception.ResourceNotFoundException;
import com.restaurant.MiniProjectPhase2.model.TableBooking;
import com.restaurant.MiniProjectPhase2.repository.UserRepository;
import com.restaurant.MiniProjectPhase2.service.TableBookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bookings")
@lombok.extern.slf4j.Slf4j
public class TableBookingController {
    private final TableBookingService service;
    private final UserRepository userRepo;

    public TableBookingController(TableBookingService s, UserRepository ur) {
        this.service = s; this.userRepo = ur;
    }

    @PostMapping
    public ResponseEntity<TableBooking> create(@Valid @RequestBody BookingRequest req) {
        var b = new TableBooking();
        b.setCustomerName(req.customerName());
        b.setBookingTime(req.bookingTime());
        b.setTableNumber(req.tableNumber());
        b.setNumberOfGuests(req.numberOfGuests());
        if (req.handledByUserId() != null) {
            b.setHandledBy(userRepo.findById(req.handledByUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found: " + req.handledByUserId())));
        }
        var saved = service.create(b);
        return ResponseEntity.created(URI.create("/bookings/" + saved.getId())).body(saved);
    }

    @GetMapping public List<TableBooking> all() { return service.findAll(); }
    @GetMapping("/{id}") public TableBooking one(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public TableBooking update(@PathVariable Long id, @Valid @RequestBody BookingRequest req) {
        var b = new TableBooking();
        b.setCustomerName(req.customerName());
        b.setBookingTime(req.bookingTime());
        b.setTableNumber(req.tableNumber());
        b.setNumberOfGuests(req.numberOfGuests());
        if (req.handledByUserId() != null) {
            b.setHandledBy(userRepo.findById(req.handledByUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found: " + req.handledByUserId())));
        }
        return service.update(id, b);
    }

    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id); return ResponseEntity.noContent().build();
    }
}
