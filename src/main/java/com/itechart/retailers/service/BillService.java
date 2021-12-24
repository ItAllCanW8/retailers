package com.itechart.retailers.service;

import com.itechart.retailers.model.dto.BillDto;
import com.itechart.retailers.model.entity.Bill;
import com.itechart.retailers.service.exception.ItemAmountException;

import java.util.List;

public interface BillService {

    void createBill(Bill bill, Long locationId, Long shopManagerId) throws ItemAmountException;

    List<BillDto> loadShopBills(Long shopId);
}
