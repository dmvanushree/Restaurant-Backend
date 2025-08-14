package com.restaurant.MiniProjectPhase2.dto;

public record UserCreateRequest(
        @jakarta.validation.constraints.NotBlank String name,
        @jakarta.validation.constraints.Email String email,
        @jakarta.validation.constraints.NotNull com.restaurant.MiniProjectPhase2.enums.Role role,
        @jakarta.validation.constraints.NotBlank String password
) {}
