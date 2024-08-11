package com.fiap.fastfood.external.messaging;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import com.fiap.fastfood.common.exceptions.custom.MessageCreationException;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSenderimpl implements MessageSender {

    @Autowired
    private AmazonSQS client;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LogManager.getLogger(MessageSenderimpl.class);


    @Override
    public SendMessageResult sendMessage(final Object object,
                                         final String id,
                                         final String queueUrl)
            throws MessageCreationException {

        return client.sendMessage(createSendMessageRequest(object, id, queueUrl));
    }

    @Override
    public SendMessageRequest createSendMessageRequest(final Object object,
                                                       final String id,
                                                       final String queueUrl) throws MessageCreationException {
        try {
            return new SendMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMessageBody(objectMapper.writeValueAsString(object))
                    .withMessageGroupId(id);
        } catch (Throwable ex) {
            logger.error(
                    "Could not create message for queue: {}, groupid: {}. Exception: {}",
                    queueUrl,
                    id,
                    ex.getMessage()
            );

            throw new MessageCreationException(
                    ExceptionCodes.SAGA_11_MESSAGE_CREATION,
                    "Error creating SQS message. Exception: " + ex.getMessage());
        }
    }
}
