package com.restaurant.MiniProjectPhase2.dto;

public record OrderCreateRequest(Long bookingId, java.util.Set<Long> itemIds) {}