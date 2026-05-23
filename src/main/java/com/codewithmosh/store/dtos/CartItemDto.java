package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemDto {
    @NotNull(message = "productId cannot be null.")
    private Long productId;

    @NotNull(message = "quantity cannot be empty.")
    @Min(value = 1, message = "quantity must be at least 1.")
    private Integer quantity;
}
