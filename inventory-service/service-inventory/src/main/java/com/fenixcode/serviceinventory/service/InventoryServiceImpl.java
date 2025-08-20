package com.fenixcode.serviceinventory.service;

import com.fenixcode.serviceinventory.dto.InventoryRequest;
import com.fenixcode.serviceinventory.dto.InventoryResponse;
import com.fenixcode.serviceinventory.model.mappers.InventoryMapper;
import com.fenixcode.serviceinventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventory;


    @Override
    public Mono<InventoryResponse> createOrder(InventoryRequest inventoryRequest) {
        var dtoRequest= InventoryMapper.INSTANCE.requestToModel(inventoryRequest);
        return inventory.existsByCode(dtoRequest.getCode())
                .flatMap(exist -> {
                    if (exist) {
                        return Mono.error(new RuntimeException("The product already exists"));
                    }
                    return inventory.save(dtoRequest)
                            .map(InventoryMapper.INSTANCE::modelToResponse);
                });
    }

    @Override
    public Mono<InventoryResponse> getInventory(String code) {
        return inventory.;
    }

    @Override
    public Flux<InventoryResponse> getInventary() {
        return null;
    }
}
