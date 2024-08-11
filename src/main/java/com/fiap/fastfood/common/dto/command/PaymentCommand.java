package com.fiap.fastfood.common.dto.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentCommand {

    private String orderId;
    private Long customerId;
    private String paymentId;
}
