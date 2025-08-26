package com.fenixcode.notification_service.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "notify_orders")
public class NotifyOrder {
    private String id;
    private String owner;
    private boolean status;
    private String dateString;
}
