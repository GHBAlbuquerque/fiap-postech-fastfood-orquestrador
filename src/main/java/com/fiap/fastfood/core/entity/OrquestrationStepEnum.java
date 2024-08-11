package com.fiap.fastfood.core.entity;

import lombok.Getter;

import java.util.Date;

@Getter
public enum OrquestrationStepEnum {
    CREATE_ORDER,
    CREATE_PAYMENT,
    CHARGE_PAYMENT,
    PREPARE_ORDER,
    COMPLETE_ORDER,
    NOTIFY_CUSTOMER,
    REVERSE_PAYMENT,
    CANCEL_PAYMENT,
    CANCEL_ORDER
}
