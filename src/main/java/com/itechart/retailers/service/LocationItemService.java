package com.itechart.retailers.service;

import com.itechart.retailers.model.payload.response.LocationItemResp;

import java.util.List;

public interface LocationItemService {

    List<LocationItemResp> getCurrentLocationItems();
}
