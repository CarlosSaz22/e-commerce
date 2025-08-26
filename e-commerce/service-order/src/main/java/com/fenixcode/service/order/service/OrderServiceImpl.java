package com.fenixcode.service.order.service;

import com.fenixcode.service.order.dto.OrderRequest;
import com.fenixcode.service.order.dto.OrderResponse;
import com.fenixcode.service.order.model.entity.OrderModel;
import com.fenixcode.service.order.model.mappers.OrderMapper;
import com.fenixcode.service.order.proxy.api.InventoryApi;
import com.fenixcode.service.order.proxy.model.InventoryResponse;
import com.fenixcode.service.order.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.reactor.ratelimiter.operator.RateLimiterOperator;
import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.reactor.timelimiter.TimeLimiterOperator;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final InventoryApi inventoryApi;
    private final CircuitBreaker circuitBreaker;
    private final Retry retry;
    private final RateLimiter rateLimiter;
    private final TimeLimiter timeLimiter;

    @Override
    public Mono<OrderResponse> createOrder(OrderRequest orderRequest) {
        var orderDto = OrderMapper.INSTANCE.requestToModel(orderRequest);
        orderDto.setDateOrder(LocalDateTime.now().toString());
        orderDto.setStatusOrder(OrderResponse.StatusEnum.PENDING.getValue());
        return orderRepository.save(orderDto)
                .map(OrderMapper.INSTANCE::modelToRequest);
    }

    @Override
    public Mono<OrderResponse> getOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(OrderMapper.INSTANCE::modelToRequest);
    }

    @Override
    public Flux<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .map(OrderMapper.INSTANCE::modelToRequest);
    }

    @Override
    public Mono<OrderResponse> updateOrder(int orderId) {
        return orderRepository.findById(orderId)
                .map(value -> {
                    value.setStatusOrder(OrderResponse.StatusEnum.COMPLETED.getValue());
                    return value;
                })
                .flatMap(value -> invokeServices(value)
                        .map(x -> value)
                        .switchIfEmpty(Mono.empty())
                ) ///  Invocación al servicio de inventario
                .flatMap(orderRepository::save)
                .map(OrderMapper.INSTANCE::modelToRequest)
                .doOnSubscribe(value -> log.info("Updating order with id: {}", orderId))
                .doOnSuccess(value -> log.info("Order updated successfully with id: {}", orderId))
                .doOnError(throwable -> log.error("Error update: {}", orderId, throwable));
    }

    private Mono<InventoryResponse> invokeServices(OrderModel value) {
        return inventoryApi.getInventory(value.getCodeProduct())
                //.transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                //.transformDeferred(RetryOperator.of(retry))
                //.transformDeferred(RateLimiterOperator.of(rateLimiter))
                .transformDeferred(TimeLimiterOperator.of(timeLimiter))
                .onErrorResume(CallNotPermittedException.class,e->fallBack());
    }

    private Mono<InventoryResponse> fallBack() {
        InventoryResponse inventoryResponse = new InventoryResponse();
        return Mono.error(new Exception("Falling back to Inventory API"));
    }
}
