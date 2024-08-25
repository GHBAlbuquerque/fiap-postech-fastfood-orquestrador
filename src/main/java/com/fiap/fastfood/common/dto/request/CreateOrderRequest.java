package com.fiap.fastfood.common.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateOrderRequest {

    @NotNull
    private Long customerId;
    @NotEmpty
    private List<OrderItemRequest> items;
}
