package com.restaurant.MiniProjectPhase2.model;
import jakarta.persistence.*;
        import java.time.LocalDateTime;

@Entity
public class Billing {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private LocalDateTime billedAt = LocalDateTime.now();

    // One-to-one owning side
    @OneToOne
    @JoinColumn(name = "booking_id", unique = true)
    private TableBooking tableBooking;

    public Billing() {}
    public Billing(double amount) { this.amount = amount; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public LocalDateTime getBilledAt() { return billedAt; }
    public void setBilledAt(LocalDateTime billedAt) { this.billedAt = billedAt; }
    public TableBooking getTableBooking() { return tableBooking; }
    public void setTableBooking(TableBooking tableBooking) { this.tableBooking = tableBooking; }
}
