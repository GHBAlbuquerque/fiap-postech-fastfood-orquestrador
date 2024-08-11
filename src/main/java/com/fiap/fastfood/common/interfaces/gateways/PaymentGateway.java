package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.dto.CustomQueueMessage;

public interface PaymentGateway {

    void commandPaymentCreation(CustomQueueMessage<String> message);

    void commandPaymentCharge(CustomQueueMessage<String> message);

    void commandPaymentReversal(CustomQueueMessage<String> message);

    void commandPaymentCancellation(CustomQueueMessage<String> message);

}
