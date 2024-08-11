package com.fiap.fastfood.core.entity;

import lombok.Getter;

import java.util.Date;

@Getter
public enum OrquestrationStepEnum {
    ORDER_REQUESTED,
    ORDER_CREATED,
    PAYMENT_CREATED,
    PAYMENT_CHARGED,
    ORDER_PREPARED,
    ORDER_COMPLETED,
    PAYMENT_REVERSED,
    PAYMENT_CANCELLED,
    ORDER_CANCELLED

}
