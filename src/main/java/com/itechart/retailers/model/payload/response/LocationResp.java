package com.itechart.retailers.model.payload.response;

import com.itechart.retailers.model.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LocationResp {
    private Location location;
    private Integer availableAmount;
}
