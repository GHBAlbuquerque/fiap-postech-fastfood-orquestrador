package com.fiap.fastfood.core.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {

    private String productId;
    private String productType;
    private Integer quantity;
    private BigDecimal itemValue;
}