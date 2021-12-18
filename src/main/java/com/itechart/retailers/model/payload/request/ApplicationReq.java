package com.itechart.retailers.model.payload.request;

import com.itechart.retailers.model.entity.Location;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ApplicationReq {

    private String applicationNumber;
    private Set<ApplicationItemReq> items;

}
