package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.dto.response.CreateOrderResponse;
import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.gateways.*;
import com.fiap.fastfood.common.interfaces.usecases.OrderCancellationOrquestratorUseCase;
import com.fiap.fastfood.common.interfaces.usecases.OrderCreationOrquestratorUseCase;
import com.fiap.fastfood.common.logging.LoggingPattern;
import com.fiap.fastfood.common.logging.TransactionInformationStorage;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.MessageHeaders;

import static com.fiap.fastfood.common.logging.Constants.HEADER_RECEIVE_COUNT;

public class ResponseGatewayImpl implements ResponseGateway {

    private final OrderCreationOrquestratorUseCase orderCreationOrquestratorUseCase;
    private final OrderCancellationOrquestratorUseCase orderCancellationOrquestratorUseCase;
    private final OrderGateway orderGateway;
    private final PaymentGateway paymentGateway;
    private final CustomerGateway customerGateway;
    private final OrquestrationGateway orquestrationGateway;

    private static final Logger logger = LogManager.getLogger(ResponseGatewayImpl.class);

    public ResponseGatewayImpl(OrderCreationOrquestratorUseCase orderCreationOrquestratorUseCase, OrderCancellationOrquestratorUseCase orderCancellationOrquestratorUseCase, OrderGateway orderGateway, PaymentGateway paymentGateway, CustomerGateway customerGateway, OrquestrationGateway orquestrationGateway) {
        this.orderCreationOrquestratorUseCase = orderCreationOrquestratorUseCase;
        this.orderCancellationOrquestratorUseCase = orderCancellationOrquestratorUseCase;
        this.orderGateway = orderGateway;
        this.paymentGateway = paymentGateway;
        this.customerGateway = customerGateway;
        this.orquestrationGateway = orquestrationGateway;
    }

    @SqsListener(queueNames = "${aws.queue_resposta_criar_pedido.url}", maxConcurrentMessages = "1", maxMessagesPerPoll="1")
    public void listenToCreateOrderResponse(MessageHeaders headers, CustomQueueMessage<CreateOrderResponse> message) throws OrderCreationException {
        logger.info(
                LoggingPattern.RESPONSE_INIT_LOG,
                message.getHeaders().getSagaId(),
                message.getHeaders().getMicrosservice()
        );

        TransactionInformationStorage.putReceiveCount(headers.get(HEADER_RECEIVE_COUNT, String.class));

        try {

            final var stepSuccessful = message.getBody().getStepSuccessful();

            if (stepSuccessful)
                orderCreationOrquestratorUseCase.orquestrate(message,
                        orderGateway,
                        paymentGateway,
                        customerGateway,
                        orquestrationGateway);
            else
                orderCancellationOrquestratorUseCase.orquestrate(message,
                        orderGateway,
                        paymentGateway,
                        orquestrationGateway);

            logger.info(LoggingPattern.RESPONSE_END_LOG,
                    message.getHeaders().getSagaId(),
                    message.getHeaders().getMicrosservice());

        } catch (Exception ex) {

            logger.info(LoggingPattern.RESPONSE_ERROR_LOG,
                    message.getHeaders().getSagaId(),
                    message.getHeaders().getMicrosservice(),
                    ex.getMessage(),
                    message.toString());

            throw new OrderCreationException(ExceptionCodes.SAGA_10_ORDER_RESPONSE_PROCESSING, ex.getMessage());
        }
    }
}
