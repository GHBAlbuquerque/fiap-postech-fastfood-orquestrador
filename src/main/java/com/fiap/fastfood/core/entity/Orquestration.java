package com.fiap.fastfood.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orquestration {

    private String id;
    private String orderId;
    private String step;
    private Date createdAt;

}
