package com.fiap.fastfood.common.exceptions.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class ExceptionDetails {

    private String type;

    private String title;

    private String code;

    private String detail;

    private Integer status;

    private Date date;

    private List<CustomError> errors;
}