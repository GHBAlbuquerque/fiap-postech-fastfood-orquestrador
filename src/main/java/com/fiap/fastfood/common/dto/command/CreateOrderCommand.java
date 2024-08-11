package com.fiap.fastfood.common.dto.command;

import com.fiap.fastfood.common.dto.request.OrderItemRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class CreateOrderCommand {

    private Long customerId;
    private List<OrderItemRequest> items;
}
