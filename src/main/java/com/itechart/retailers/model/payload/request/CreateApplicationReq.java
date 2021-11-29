package com.itechart.retailers.model.payload.request;

import com.itechart.retailers.model.dto.ItemDto;
import com.itechart.retailers.model.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreateApplicationReq {
    private String applicationNumber;
    private String status;
    private String itemsTotal;
    private String unitsTotal;
    private String srcLocation;
    private String destLocation;
    private UserDto createdBy;
    private Set<ItemDto> items;
}
