package com.fiap.fastfood.common.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {

    @NotBlank
    private String productId;
    @NotBlank
    private String productType;
    @NotNull
    private Integer quantity;
    @NotNull
    private BigDecimal productValue;
}
