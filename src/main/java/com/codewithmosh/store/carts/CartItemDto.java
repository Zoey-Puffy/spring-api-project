package com.codewithmosh.store.carts;

import com.codewithmosh.store.dtos.CartProductDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private CartProductDto product;

    private int quantity;

    private BigDecimal totalPrice;
}
