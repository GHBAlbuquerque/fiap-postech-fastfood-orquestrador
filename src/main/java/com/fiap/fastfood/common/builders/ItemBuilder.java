package com.fiap.fastfood.common.builders;

import com.fiap.fastfood.common.dto.request.OrderItemRequest;
import com.fiap.fastfood.core.entity.Item;

public class ItemBuilder {

    public static Item fromRequestToDomain(OrderItemRequest request) {
        return new Item()
                .setProductId(request.getProductId())
                .setProductType(request.getProductType())
                .setQuantity(request.getQuantity())
                .setItemValue(request.getProductValue());

    }
}
