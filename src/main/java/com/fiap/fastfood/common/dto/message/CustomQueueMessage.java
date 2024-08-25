package com.fiap.fastfood.common.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@AllArgsConstructor
public class CustomQueueMessage<T> {

    private CustomMessageHeaders headers;
    private T body;
}
