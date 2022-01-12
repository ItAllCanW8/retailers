package com.itechart.retailers.service;

import com.itechart.retailers.model.dto.BillDto;
import com.itechart.retailers.model.entity.Bill;
import com.itechart.retailers.service.exception.BillAlreadyExistsException;
import com.itechart.retailers.service.exception.ItemAmountException;
import com.itechart.retailers.service.exception.ItemNotFoundException;

import java.util.List;

/**
 * The interface Bill service.
 */
public interface BillService {

    /**
     * Create bill bill.
     *
     * @param bill          the bill
     * @param locationId    the location id
     * @param shopManagerId the shop manager id
     * @return the bill
     * @throws ItemAmountException        the item amount exception
     * @throws ItemNotFoundException      the item not found exception
     * @throws BillAlreadyExistsException the bill already exists exception
     */
    Bill createBill(Bill bill, Long locationId, Long shopManagerId) throws ItemAmountException, ItemNotFoundException, BillAlreadyExistsException;

    /**
     * Load shop bills list.
     *
     * @param shopId the shop id
     * @return the list
     */
    List<BillDto> loadShopBills(Long shopId);
}
