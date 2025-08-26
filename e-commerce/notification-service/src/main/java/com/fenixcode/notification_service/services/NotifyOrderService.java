package com.fenixcode.notification_service.services;

import com.fenixcode.notification_service.dto.NotifyResponse;
import com.fenixcode.notification_service.models.dto.ListNotify;
import com.fenixcode.notification_service.models.entity.NotifyOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NotifyOrderService {
    Mono<Boolean> save(ListNotify listNotify);
    Mono<NotifyResponse> getNotify(String id);

    Flux<NotifyResponse> getAllNotify();
}
