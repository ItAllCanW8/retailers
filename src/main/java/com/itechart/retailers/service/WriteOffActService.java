package com.itechart.retailers.service;

import com.itechart.retailers.model.dto.WriteOffActDto;
import com.itechart.retailers.model.entity.WriteOffAct;
import com.itechart.retailers.service.exception.ItemAmountException;
import com.itechart.retailers.service.exception.WriteOffActAlreadyExistsException;

import java.util.List;

/**
 * The interface Write off act service.
 */
public interface WriteOffActService {
    /**
     * Load customer write off acts list.
     *
     * @return the list
     */
    List<WriteOffActDto> loadCustomerWriteOffActs();

    /**
     * Save write off act.
     *
     * @param writeOffAct the write off act
     * @return the write off act
     * @throws ItemAmountException               the item amount exception
     * @throws WriteOffActAlreadyExistsException the write off act already exists exception
     */
    WriteOffAct save(WriteOffAct writeOffAct) throws ItemAmountException, WriteOffActAlreadyExistsException;

    /**
     * Load local write off acts list.
     *
     * @return the list
     */
    List<WriteOffActDto> loadLocalWriteOffActs();
}
