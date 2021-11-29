package com.itechart.retailers.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {
    private String upc;
    private Integer amount;
    private Float cost;
}
