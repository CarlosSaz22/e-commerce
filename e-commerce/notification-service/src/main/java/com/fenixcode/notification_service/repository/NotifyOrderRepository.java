package com.fenixcode.notification_service.repository;

import com.fenixcode.notification_service.models.entity.NotifyOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface NotifyOrderRepository extends ReactiveCrudRepository<NotifyOrder, String> {
}
