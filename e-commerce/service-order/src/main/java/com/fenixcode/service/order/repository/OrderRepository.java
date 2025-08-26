package com.fenixcode.service.order.repository;

import com.fenixcode.service.order.model.entity.OrderModel;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends R2dbcRepository<OrderModel,Integer> {
}
