package com.itechart.retailers.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CustomerPageResp {
    List<CustomerResp> customers;
    private Integer totalPages;
}
