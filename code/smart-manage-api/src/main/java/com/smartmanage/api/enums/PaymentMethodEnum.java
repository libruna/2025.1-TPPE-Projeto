package com.smartmanage.api.enums;

import lombok.Getter;

@Getter
public enum PaymentMethodEnum {

    CARD("CARD"),
    PIX("ONLINE_PAYMENT"),
    BOLETO("BOLETO");

    private final String method;

    PaymentMethodEnum(String method) {
        this.method = method;
    }
}
