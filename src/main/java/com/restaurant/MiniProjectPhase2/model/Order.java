package com.restaurant.MiniProjectPhase2.model;

import com.restaurant.MiniProjectPhase2.enums.OrderStatus;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "table_booking_id")
    private TableBooking tableBooking;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private OrderStatus status = OrderStatus.PLACED;
    //with menuitems

    @ManyToMany
    @JoinTable(
            name = "order_menu_items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_item_id")
    )
    private Set<MenuItem> items = new HashSet<>();

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public TableBooking getTableBooking() {
        return tableBooking;
    }

    public void setTableBooking(TableBooking tableBooking) {
        this.tableBooking = tableBooking;
    }

    public Set<MenuItem> getItems() {
        return items;
    }

    public void setItems(Set<MenuItem> items) {
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(long l) {
    }

    public Order getBody() {
        return null;
    }
}
