package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateCartItemRequest {
    @Min(value = 1, message = "数量必须大于零")
    private int quantity;
}
