package com.restaurant.MiniProjectPhase2.controller;

import com.restaurant.MiniProjectPhase2.dto.OrderCreateRequest;
import com.restaurant.MiniProjectPhase2.dto.OrderStatusUpdateRequest;
import com.restaurant.MiniProjectPhase2.enums.OrderStatus;
import com.restaurant.MiniProjectPhase2.exception.ResourceNotFoundException;
import com.restaurant.MiniProjectPhase2.model.Order;
import com.restaurant.MiniProjectPhase2.repository.MenuItemRepository;
import com.restaurant.MiniProjectPhase2.repository.TableBookingRepository;
import com.restaurant.MiniProjectPhase2.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@lombok.extern.slf4j.Slf4j
public class OrderController {

    private final OrderService service;
    private final TableBookingRepository bookingRepo;
    private final MenuItemRepository menuRepo;

    public OrderController(OrderService service, TableBookingRepository bookingRepo, MenuItemRepository menuRepo) {
        this.service = service;
        this.bookingRepo = bookingRepo;
        this.menuRepo = menuRepo;
    }

    // Create a new order
    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderCreateRequest req) {
        var booking = bookingRepo.findById(req.bookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found: " + req.bookingId()));

        var order = new Order();
        order.setTableBooking(booking);
        order.setStatus(OrderStatus.PLACED);

        if (req.itemIds() != null && !req.itemIds().isEmpty()) {
            var items = req.itemIds().stream()
                    .map(id -> menuRepo.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found: " + id)))
                    .collect(Collectors.toSet());
            order.setItems(items);
        }

        var saved = service.create(order);
        return ResponseEntity.created(URI.create("/orders/" + saved.getId())).body(saved);
    }

    // Get all orders
    @GetMapping
    public List<Order> all() {
        return service.findAll();
    }

    // Get a single order by ID
    @GetMapping("/{id}")
    public Order one(@PathVariable Long id) {
        return service.findById(id);
    }

    // Update order status
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestBody OrderStatusUpdateRequest req) {
        Order updated = service.updateStatus(id, req.status().toString());
        return ResponseEntity.ok(updated);
    }

    // Add menu items to an existing order
    @PostMapping("/{id}/items")
    public ResponseEntity<Order> addItems(@PathVariable Long id, @RequestBody Set<Long> menuItemIds) {
        Order updated = service.addMenuItems(id, menuItemIds);
        return ResponseEntity.ok(updated);
    }
}
