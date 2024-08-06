package com.fiap.fastfood.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CustomQueueMessage<T> {

    private Map<String, String> headers;
    private T body;
}
