package com.itechart.retailers.model.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdRentalTaxReq {

    private Long shopId;
    private Float newRentalTax;
}
