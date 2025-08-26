package com.fenixcode.service.order.model.mappers;

import com.fenixcode.service.order.dto.OrderRequest;
import com.fenixcode.service.order.dto.OrderResponse;
import com.fenixcode.service.order.model.entity.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "codeProduct", source = "codeProduct")
    OrderModel requestToModel(OrderRequest orderRequest);

    @Mapping(target = "status", source = "statusOrder", qualifiedByName = "mapStatus")
    @Mapping(target = "createdAt", source = "dateOrder")
    OrderResponse modelToRequest(OrderModel orderModel);

    @Named("mapStatus")
    default OrderResponse.StatusEnum mapStatus(String statusOrder) {
        return OrderResponse.StatusEnum.fromValue(statusOrder);
    }
}
