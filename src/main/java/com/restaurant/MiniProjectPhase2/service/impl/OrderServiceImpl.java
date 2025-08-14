package com.restaurant.MiniProjectPhase2.service.impl;

import com.restaurant.MiniProjectPhase2.enums.OrderStatus;
import com.restaurant.MiniProjectPhase2.exception.ResourceNotFoundException;
import com.restaurant.MiniProjectPhase2.model.MenuItem;
import com.restaurant.MiniProjectPhase2.model.Order;
import com.restaurant.MiniProjectPhase2.model.TableBooking;
import com.restaurant.MiniProjectPhase2.repository.MenuItemRepository;
import com.restaurant.MiniProjectPhase2.repository.OrderRepository;
import com.restaurant.MiniProjectPhase2.repository.TableBookingRepository;
import com.restaurant.MiniProjectPhase2.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final TableBookingRepository bookingRepo;
    private final MenuItemRepository menuRepo;

    public OrderServiceImpl(OrderRepository orderRepo,
                            TableBookingRepository bookingRepo,
                            MenuItemRepository menuRepo) {
        this.orderRepo = orderRepo;
        this.bookingRepo = bookingRepo;
        this.menuRepo = menuRepo;
    }

    @Override
    public Order create(Order order) {
        log.info("Creating order for booking " + order.getTableBooking().getId());
        Long bookingId = order.getTableBooking().getId();
        TableBooking tb = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found: " + bookingId));
        order.setTableBooking(tb);
        if (order.getStatus() == null) order.setStatus(com.restaurant.MiniProjectPhase2.enums.OrderStatus.PLACED);
        return orderRepo.save(order);
    }

    @Override public List<Order> findAll() { return orderRepo.findAll(); }

    @Override public Order findById(Long id) {
        return orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
    }

    @Override
    public Order updateStatus(Long id, String status) {
        Order o = findById(id);
        var parsed = com.restaurant.MiniProjectPhase2.enums.OrderStatus.valueOf(status.toUpperCase());
        log.info("Updating order {} status to {}");
        o.setStatus(parsed);
        return orderRepo.save(o);
    }

    @Override
    public Order addMenuItems(Long orderId, java.util.Set<Long> menuItemIds) {
        Order o = findById(orderId);
        var items = menuItemIds.stream()
                .map(i -> menuRepo.findById(i).orElseThrow(() -> new ResourceNotFoundException("MenuItem not found: " + i)))
                .collect(java.util.stream.Collectors.toSet());
        o.getItems().addAll(items);
        log.info("Added {} items to order {}");
        return orderRepo.save(o);
    }
}

