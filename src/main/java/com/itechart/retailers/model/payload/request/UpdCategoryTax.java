package com.itechart.retailers.model.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdCategoryTax {

    private Long customerCategoryId;
    private Float newCategoryTax;
}
