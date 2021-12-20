package com.itechart.retailers.model.payload.response;

import com.itechart.retailers.model.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerResp {

    private Customer customer;
    private String email;

}
