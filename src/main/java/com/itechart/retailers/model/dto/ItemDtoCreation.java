package com.itechart.retailers.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDtoCreation {

	private String upc;
	private String label;
	private int units;
	private String categoryName;

}
