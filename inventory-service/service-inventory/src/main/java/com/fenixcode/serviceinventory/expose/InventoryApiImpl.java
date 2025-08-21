package com.fenixcode.serviceinventory.expose;

import com.fenixcode.serviceinventory.api.InventoryApi;
import com.fenixcode.serviceinventory.api.InventoryApiDelegate;
import com.fenixcode.serviceinventory.dto.InventoryRequest;
import com.fenixcode.serviceinventory.dto.InventoryResponse;
import com.fenixcode.serviceinventory.repository.InventoryRepository;
import com.fenixcode.serviceinventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@Slf4j
@RequiredArgsConstructor
public class InventoryApiImpl implements InventoryApiDelegate {

    private final InventoryService inventoryService;

    @Override
    public Mono<InventoryResponse> getInventory(String productId,
                                                ServerWebExchange exchange) {
        return inventoryService.getInventory(productId);
    }

    @Override
    public Flux<InventoryResponse> listInventory(ServerWebExchange exchange) {
        return inventoryService.getInventary().delayElements(Duration.ofSeconds(5));
    }

    @Override
    public Mono<InventoryResponse> registerInventory(Mono<InventoryRequest> inventoryRequest,
                                                     ServerWebExchange exchange) {
        return inventoryRequest.flatMap(inventoryService::createOrder);
    }

}
