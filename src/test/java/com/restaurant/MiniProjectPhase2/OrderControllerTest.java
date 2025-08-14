package com.restaurant.MiniProjectPhase2;

import com.restaurant.MiniProjectPhase2.controller.OrderController;
import com.restaurant.MiniProjectPhase2.dto.OrderStatusUpdateRequest;
import com.restaurant.MiniProjectPhase2.enums.OrderStatus;
import com.restaurant.MiniProjectPhase2.exception.ResourceNotFoundException;
import com.restaurant.MiniProjectPhase2.model.Order;
import com.restaurant.MiniProjectPhase2.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @InjectMocks
    private OrderController controller;

    @Mock
    private OrderService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllOrders() {
        when(service.findAll()).thenReturn(List.of(new Order(), new Order()));
        List<Order> list = controller.all();
        assertEquals(2, list.size());
    }

    @Test
    void findOrderById_notFound() {
        when(service.findById(99L)).thenThrow(new ResourceNotFoundException("Order not found"));
        assertThrows(ResourceNotFoundException.class, () -> controller.one(99L));
    }

    @Test
    void updateOrderStatus_success() {
        OrderStatusUpdateRequest req = new OrderStatusUpdateRequest(OrderStatus.DELIVERED);
        Order o = new Order();
        o.setId(7L);
        o.setStatus(OrderStatus.DELIVERED);

        when(service.updateStatus(eq(7L), anyString())).thenReturn(o);

        var response = controller.updateStatus(7L, req);
        assertEquals(OrderStatus.DELIVERED, response.getBody().getStatus());
    }
}
