package com.fiap.fastfood.common.interfaces.external;

import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fiap.fastfood.common.exceptions.custom.MessageCreationException;

public interface MessageSender {

    SendMessageResult sendMessage(final Object object,
                                  final String id,
                                  final String queueUrl) throws MessageCreationException;

    SendMessageRequest createSendMessageRequest(final Object object,
                                                final String id,
                                                final String queueUrl) throws MessageCreationException;
}
