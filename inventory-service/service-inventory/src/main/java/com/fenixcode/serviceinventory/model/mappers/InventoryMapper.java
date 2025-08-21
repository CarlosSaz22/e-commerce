package com.fenixcode.serviceinventory.model.mappers;

import com.fenixcode.serviceinventory.dto.InventoryRequest;
import com.fenixcode.serviceinventory.dto.InventoryResponse;
import com.fenixcode.serviceinventory.model.entity.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryMapper INSTANCE = Mappers.getMapper(InventoryMapper.class);


    @Mapping(target="code",source ="idProduct")
    ProductModel requestToModel(InventoryRequest inventoryRequest);

    @Mapping(target="idProduct",source ="code")
    InventoryResponse modelToResponse(ProductModel productModel);
}
