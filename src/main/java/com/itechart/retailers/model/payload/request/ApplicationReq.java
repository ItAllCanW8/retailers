package com.itechart.retailers.model.payload.request;

import com.itechart.retailers.model.dto.ItemDto;
import com.itechart.retailers.model.entity.Application;
import com.itechart.retailers.model.entity.ApplicationItem;
import com.itechart.retailers.model.entity.Item;
import com.itechart.retailers.model.entity.Location;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ApplicationReq {
    private String applicationNumber;
    private Location location;
    private Set<ApplicationItemReq> items;
}
