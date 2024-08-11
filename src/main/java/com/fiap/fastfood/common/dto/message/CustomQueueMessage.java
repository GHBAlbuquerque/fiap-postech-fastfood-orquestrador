package com.fiap.fastfood.common.dto.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomQueueMessage<T> {

    private CustomMessageHeaders headers;
    private T body;
}
