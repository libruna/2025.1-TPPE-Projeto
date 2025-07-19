package com.smartmanage.api.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {

    PAYMENT_PENDING("PAYMENT_PENDING"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED"),;

    private final String status;

    OrderStatusEnum(String status) {
        this.status = status;
    }
}
