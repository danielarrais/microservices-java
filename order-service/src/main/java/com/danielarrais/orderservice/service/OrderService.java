package com.danielarrais.orderservice.service;

import com.danielarrais.orderservice.dto.OrderLineItemsDTO;
import com.danielarrais.orderservice.dto.OrderRequest;
import com.danielarrais.orderservice.model.Order;
import com.danielarrais.orderservice.model.OrderLineItems;
import com.danielarrais.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        List<OrderLineItems> orderLineItems =
                orderRequest.getOrderLineItems()
                        .stream().map(this::mapToDTO).toList();

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItems(orderLineItems).build();

        orderRepository.save(order);
    }

    private OrderLineItems mapToDTO(OrderLineItemsDTO orderLineItems) {
        return OrderLineItems.builder()
                .price(orderLineItems.getPrice())
                .quantity(orderLineItems.getQuantity())
                .skuCode(orderLineItems.getSkuCode())
                .build();
    }
}
