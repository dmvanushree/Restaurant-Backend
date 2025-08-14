package com.restaurant.MiniProjectPhase2.dto;

public record BookingRequest(
        @jakarta.validation.constraints.NotBlank String customerName,
        @jakarta.validation.constraints.NotNull java.time.LocalDateTime bookingTime,
        int tableNumber,
        int numberOfGuests,
        Long handledByUserId
) {}