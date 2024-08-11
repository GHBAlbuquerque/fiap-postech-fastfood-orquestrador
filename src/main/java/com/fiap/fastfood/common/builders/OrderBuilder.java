package com.fiap.fastfood.common.builders;

import com.fiap.fastfood.common.dto.request.CreateOrderRequest;
import com.fiap.fastfood.core.entity.Order;

public class OrderBuilder {

    public static Order fromRequestToDomain(CreateOrderRequest request) {
        return Order.builder()
                .customerId(request.getCustomerId())
                .items(
                        request.getItems()
                                .stream()
                                .map(ItemBuilder::fromRequestToDomain)
                                .toList()
                )
                .build();
    }

}
