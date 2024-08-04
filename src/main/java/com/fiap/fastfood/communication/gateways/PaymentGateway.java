package com.fiap.fastfood.communication.gateways;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PaymentGateway {

    private static final Logger logger = LogManager.getLogger(PaymentGateway.class);

    @SqsListener({"${my.queue.url}", "myOtherQueue"})
    public void listenTwoQueues(String message) {
        System.out.println(message);
    }
}
