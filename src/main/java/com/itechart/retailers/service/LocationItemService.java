package com.itechart.retailers.service;

import com.itechart.retailers.model.payload.response.LocationItemResp;

import java.util.List;

/**
 * The interface Location item service.
 */
public interface LocationItemService {

    /**
     * Gets current location items.
     *
     * @return the current location items
     */
    List<LocationItemResp> getCurrentLocationItems();
}
