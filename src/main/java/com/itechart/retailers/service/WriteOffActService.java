package com.itechart.retailers.service;

import com.itechart.retailers.model.dto.WriteOffActDto;
import com.itechart.retailers.model.entity.WriteOffAct;
import com.itechart.retailers.service.exception.ItemAmountException;
import com.itechart.retailers.service.exception.WriteOffActAlreadyExistsException;

import java.util.List;

public interface WriteOffActService {
    List<WriteOffActDto> loadCustomerWriteOffActs();

    WriteOffAct save(WriteOffAct writeOffAct) throws ItemAmountException, WriteOffActAlreadyExistsException;

    List<WriteOffActDto> loadLocalWriteOffActs();
}
