package com.fiap.fastfood.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomQueueMessage<T> {

    private CustomMessageHeaders headers;
    private T body;
}
