package com.fenixcode.notification_service.services;

import com.fenixcode.notification_service.dto.NotifyResponse;
import com.fenixcode.notification_service.models.dto.ListNotify;
import com.fenixcode.notification_service.models.entity.NotifyOrder;
import com.fenixcode.notification_service.repository.NotifyOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotifyOrderServiceImpl implements NotifyOrderService {
    private final NotifyOrderRepository notifyOrderRepository;

    @Override
    public Mono<Boolean> save(ListNotify listNotify) {
        var data = new NotifyOrder();
        data.setOwner(listNotify.owner());
        data.setStatus(listNotify.status());
        data.setDateString(LocalDateTime.now().toString());
        return notifyOrderRepository.save(data)
                .map(notifyOrder -> notifyOrder.getId() != null ? true : false)
                .doOnSuccess(notifyOrder -> log.info("Notify saved :"+notifyOrder))
                .doOnSubscribe(notifyOrder -> log.info("Saving notify..."))
                .doOnError(notifyOrder -> log.info("Error saving notify." + notifyOrder.getMessage()));
    }

    @Override
    public Mono<NotifyResponse> getNotify(String id) {
        return notifyOrderRepository.findById(id)
                .map(notifyOrder -> new NotifyResponse()
                        .id(notifyOrder.getId())
                        .owner(notifyOrder.getOwner())
                        .status(notifyOrder.isStatus())
                        .dateString(notifyOrder.getDateString()));
    }

    @Override
    public Flux<NotifyResponse> getAllNotify() {
        return notifyOrderRepository.findAll()
                .map(notifyOrder -> new NotifyResponse()
                        .id(notifyOrder.getId())
                        .owner(notifyOrder.getOwner())
                        .status(notifyOrder.isStatus())
                        .dateString(notifyOrder.getDateString()));
    }
}
