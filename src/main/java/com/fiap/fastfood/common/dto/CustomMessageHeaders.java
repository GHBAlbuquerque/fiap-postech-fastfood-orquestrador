package com.fiap.fastfood.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomMessageHeaders {
    private String orderId;
    private String microsservice;
    private String timestamp;
}
