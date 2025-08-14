package com.restaurant.MiniProjectPhase2.service;

import com.restaurant.MiniProjectPhase2.model.Order;
import java.util.List;
import java.util.Set;

public interface OrderService {
    Order create(Order order);
    List<Order> findAll();
    Order findById(Long id);
    Order updateStatus(Long id, String status);
    Order addMenuItems(Long orderId, Set<Long> menuItemIds);
}
