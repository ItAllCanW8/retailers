package com.itechart.retailers.model.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationItemReq {

    private String upc;
    private Integer amount;
    private Float cost;

}
