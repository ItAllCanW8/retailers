package com.itechart.retailers.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LocationItemResp {
    private String upc;
    private String label;
    private Integer amount;
    private Float cost;
}
