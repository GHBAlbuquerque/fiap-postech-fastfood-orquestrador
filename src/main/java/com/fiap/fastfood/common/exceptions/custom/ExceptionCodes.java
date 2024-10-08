package com.fiap.fastfood.common.exceptions.custom;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionCodes {

    SAGA_01_CUSTOMER_NOTIFICATION,
    SAGA_02_ORDER_CREATION,
    SAGA_03_ORDER_PREPARATION,
    SAGA_04_ORDER_COMPLETION,
    SAGA_05_PAYMENT_CREATION,
    SAGA_06_PAYMENT_CHARGE,
    SAGA_07_PAYMENT_REVERSAL,
    SAGA_08_PAYMENT_CANCELLATION,
    SAGA_09_ORDER_CANCELLATION,
    SAGA_10_ORDER_RESPONSE_PROCESSING,
    SAGA_11_MESSAGE_CREATION,
    SAGA_12_ORQUESTRATION_STEP_NR,
    SAGA_13_ORQUESTRATION_RECORD_404

}
