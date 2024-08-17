package com.fiap.fastfood.common.logging;

import com.fiap.fastfood.common.dto.message.CustomMessageHeaders;
import org.slf4j.MDC;

public class TransactionInformationStorage {

    public static void fill(CustomMessageHeaders headers) {

        MDC.put("sagaId", headers.getSagaId());
        MDC.put("orderId", headers.getOrderId());
        MDC.put("messageType", headers.getMessageType());
        MDC.put("microsservice", headers.getMicrosservice());
    }

    public static void putReceiveCount(String receiveCount) {
        MDC.put("receiveCount", receiveCount);
    }

    public static void putPaymentID(String paymentId) {
        MDC.put("paymentId", paymentId);
    }

    public static String getSagaId() {
        return MDC.get("sagaId");
    }

    public static String getPaymentId() {
        return MDC.get("paymentId");
    }

    public static String getReceiveCount() {
        return MDC.get("receiveCount");
    }

    private TransactionInformationStorage() {
    }
}
