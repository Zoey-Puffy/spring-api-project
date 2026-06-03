package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.OrderDto;
import com.codewithmosh.store.mappers.OrderMapper;
import com.codewithmosh.store.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final OrderMapper orderMapper;

    public List<OrderDto> getOrders(){
        // 1.get current user
        var user = authService.getCurrentUser();
        // 2.check orders by userId -- findByCustomerId
        var orders = orderRepository.findByCustomerId(user.getId());
//        if(orders.isEmpty()){
//        }
        // 3.return List of orderId
        // 4. toDto
        return orders.stream().map(orderMapper::toDto).toList();
    }
}
