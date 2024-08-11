package com.fiap.fastfood.common.dto.response;

import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateOrderResponse {

    private String orderId;
    private String customerId;
    private String paymentId;
    private OrquestrationStepEnum executedStep;
    private Boolean stepSuccessful;

}
