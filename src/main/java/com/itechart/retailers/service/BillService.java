package com.itechart.retailers.service;

import com.itechart.retailers.model.dto.BillDto;
import com.itechart.retailers.model.entity.Bill;
import com.itechart.retailers.service.exception.ItemAmountException;
import com.itechart.retailers.service.exception.UndefinedItemException;

import java.util.List;

public interface BillService {

    Bill createBill(Bill bill, Long locationId, Long shopManagerId) throws ItemAmountException, UndefinedItemException;

    List<BillDto> loadShopBills(Long shopId);
}
