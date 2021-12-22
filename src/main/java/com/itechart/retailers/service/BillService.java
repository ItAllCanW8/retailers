package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Bill;

public interface BillService {

    void createBill(Bill bill, Long locationId, Long shopManagerId);
}
