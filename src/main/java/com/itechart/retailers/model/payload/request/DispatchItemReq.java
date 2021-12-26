package com.itechart.retailers.model.payload.request;

import com.itechart.retailers.model.payload.response.LocationItemResp;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DispatchItemReq {
    private String applicationNumber;
    private String destLocation;
    private List<LocationItemResp> itemsToDispatch;
}
