package com.fenixcode.serviceinventory.service;

import com.fenixcode.serviceinventory.dto.InventoryRequest;
import com.fenixcode.serviceinventory.dto.InventoryResponse;
import com.fenixcode.serviceinventory.model.entity.ProductModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InventoryService {
    Mono<InventoryResponse> createOrder(InventoryRequest inventoryRequest);
    Mono<InventoryResponse> getInventory(String code);
    Flux<InventoryResponse> getInventary();
}
