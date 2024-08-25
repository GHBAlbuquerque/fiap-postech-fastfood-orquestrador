package com.fiap.fastfood.core.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
public class Order {

    private Long customerId;
    private List<Item> items;
}
