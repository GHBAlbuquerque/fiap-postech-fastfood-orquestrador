package com.fiap.fastfood.common.builders;

import com.fiap.fastfood.common.dto.request.CreateOrderRequest;
import com.fiap.fastfood.core.entity.Order;

public class OrderBuilder {

    public static Order fromRequestToDomain(CreateOrderRequest request) {
        return new Order()
                .setCustomerId(request.getCustomerId())
                .setItems(
                        request.getItems()
                                .stream()
                                .map(ItemBuilder::fromRequestToDomain)
                                .toList()
                );
    }

}
