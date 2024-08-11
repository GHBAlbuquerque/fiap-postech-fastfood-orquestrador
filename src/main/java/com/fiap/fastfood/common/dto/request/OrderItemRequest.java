package com.fiap.fastfood.common.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
