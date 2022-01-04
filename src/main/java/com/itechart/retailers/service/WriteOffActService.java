package com.itechart.retailers.service;

import com.itechart.retailers.model.dto.WriteOffActDto;
import com.itechart.retailers.model.entity.WriteOffAct;
import com.itechart.retailers.service.exception.ItemAmountException;

import java.util.List;

public interface WriteOffActService {
    List<WriteOffActDto> loadCustomerWriteOffActs();

    WriteOffAct save(WriteOffAct writeOffAct) throws ItemAmountException;

    List<WriteOffActDto> loadLocalWriteOffActs();
}
