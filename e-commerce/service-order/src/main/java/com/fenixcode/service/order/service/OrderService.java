package com.fenixcode.service.order.service;

import com.fenixcode.service.order.dto.OrderRequest;
import com.fenixcode.service.order.dto.OrderResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {
    Mono<OrderResponse> createOrder(OrderRequest orderRequest);
    Mono<OrderResponse> getOrderById(Integer orderId);
    Flux<OrderResponse> getAllOrders();
    Mono<OrderResponse> updateOrder(int orderId);
}
