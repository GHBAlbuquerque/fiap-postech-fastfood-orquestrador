package com.fiap.fastfood.common.dto.command;

import com.fiap.fastfood.core.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class CreateOrderCommand {

    private Long customerId;
    private List<Item> items;
}
