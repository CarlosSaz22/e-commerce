package com.fenixcode.notification_service.listener;

import com.fenixcode.notification_service.models.dto.ListNotify;
import com.fenixcode.notification_service.models.entity.NotifyOrder;
import com.fenixcode.notification_service.services.NotifyOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final NotifyOrderService notifyOrderService;

    @KafkaListener(topics = "orders-topic",groupId = "micro-order")
    public Mono<Void> listen(String message){
        log.info("received OrderEventListener message: {}", message);
        var data=new ListNotify(0,message,true);
        return notifyOrderService.save(data)
                .then();
    }
}
