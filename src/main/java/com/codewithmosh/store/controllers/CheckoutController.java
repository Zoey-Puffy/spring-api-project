package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.CheckoutRequest;
import com.codewithmosh.store.dtos.CheckoutResponse;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.entities.OrderItem;
import com.codewithmosh.store.entities.OrderStatus;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.OrderRepository;
import com.codewithmosh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;

@RestController
@AllArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<CheckoutResponse> createOrder(
            @RequestBody CheckoutRequest cartRequest
    ) {
        var cart = cartRepository.findById(cartRequest.getCartId()).orElse(null);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var customer = userRepository.findById((Long) authentication.getPrincipal()).orElse(null);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        // create an order, add cartItems to orderItems
        // id, customer, status, createdAt, totalPrice, items
        // auto created: id, createdAt
        var order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalPrice(cart.getTotalPrice());

        // transfer cartItems to orderItems
        // orderItems: id, order, product, unitPrice, quantity, totalPrice
        // CartItems: id, cart, product, quantity, getTotalPrice()
        // 为什么没有order entity中的setItems method呢？
        // product.getPrice()
        var orderItems = new LinkedHashSet<OrderItem>();
        cart.getItems().forEach(item -> {
            var orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setUnitPrice(item.getProduct().getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice(item.getTotalPrice());
            orderItems.add(orderItem);
        });

        order.setItems(orderItems);

        // save
        orderRepository.save(order);
        // return
        return ResponseEntity.ok(new CheckoutResponse(order.getId()));
    }
}
