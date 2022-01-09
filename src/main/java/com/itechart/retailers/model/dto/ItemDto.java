package com.itechart.retailers.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private String upc;
    private Integer amount;
    private Float cost;
}
