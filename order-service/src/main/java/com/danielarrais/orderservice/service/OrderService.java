package com.danielarrais.orderservice.service;

import com.danielarrais.orderservice.dto.InventoryResponse;
import com.danielarrais.orderservice.dto.OrderLineItemsDTO;
import com.danielarrais.orderservice.dto.OrderRequest;
import com.danielarrais.orderservice.model.Order;
import com.danielarrais.orderservice.model.OrderLineItems;
import com.danielarrais.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest) {
        List<OrderLineItems> orderLineItems =
                orderRequest.getOrderLineItems()
                        .stream().map(this::mapToDTO).toList();

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItems(orderLineItems).build();

        List<String> skuCodes = order.getOrderLineItems().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        InventoryResponse[] inStockResponse = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("sku-codes", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = stream(requireNonNull(inStockResponse))
                .allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }

    }

    private OrderLineItems mapToDTO(OrderLineItemsDTO orderLineItems) {
        return OrderLineItems.builder()
                .price(orderLineItems.getPrice())
                .quantity(orderLineItems.getQuantity())
                .skuCode(orderLineItems.getSkuCode())
                .build();
    }
}
