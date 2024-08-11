package com.fiap.fastfood.common.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
public class CustomMessageHeaders {

    private String sagaId;
    private String orderId;
    private String messageType;
    private String microsservice;
    private Integer receiveCount = 0;

    public CustomMessageHeaders(String sagaId, String orderId, String messageType, String microsservice) {
        this.sagaId = sagaId;
        this.orderId = orderId;
        this.messageType = messageType;
        this.microsservice = microsservice;
    }
}
