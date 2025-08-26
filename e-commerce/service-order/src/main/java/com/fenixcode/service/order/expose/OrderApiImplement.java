package com.fenixcode.service.order.expose;

import com.fenixcode.service.order.api.OrdenesApiDelegate;
import com.fenixcode.service.order.dto.OrderRequest;
import com.fenixcode.service.order.dto.OrderResponse;
import com.fenixcode.service.order.proxy.ApiClient;
import com.fenixcode.service.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderApiImplement implements OrdenesApiDelegate {

    private final OrderService orderService;

    @Override
    public Mono<OrderResponse> createOrder(Mono<OrderRequest> orderRequest,
                                            ServerWebExchange exchange) {

      return orderRequest.flatMap(orderService::createOrder)
              .doOnSubscribe(value -> log.info("Creating order "))
              .doOnSuccess(value-> log.info("Order created successfully"))
              .doOnError(throwable-> log.error("Error creating order :",throwable));
    }

    @Override
    public Mono<OrderResponse> getOrder(Integer orderId,
                                         ServerWebExchange exchange) {
        return orderService.getOrderById(orderId)
                .doOnSubscribe(value -> {
                    if(Optional.ofNullable(value).isEmpty()){
                        exchange.getResponse().setStatusCode(HttpStatus.NO_CONTENT);
                    }
                })
                .doOnSuccess(value-> log.info("Getting order with id : {}",orderId))
                .doOnError(throwable-> log.error("Error getting order with id : {}",orderId,throwable));
    }

    @Override
    public Flux<OrderResponse> listOrders(ServerWebExchange exchange) {
        return orderService.getAllOrders();
    }
    @Override
    public Mono<Void> updateOrder(Integer orderId,
                                   ServerWebExchange exchange) {
        return orderService.updateOrder(orderId)
                .doOnSubscribe(value -> {
                    if(Optional.ofNullable(value).isEmpty()){
                        exchange.getResponse().setStatusCode(HttpStatus.NO_CONTENT);
                    }
                })
                .then();
    }


}
