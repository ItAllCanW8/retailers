package com.itechart.retailers.model.payload.response;

import com.itechart.retailers.model.entity.Customer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerWithMail {

	private Customer customer;
	private String mail;

}
