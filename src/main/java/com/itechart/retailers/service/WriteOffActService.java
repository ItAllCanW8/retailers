package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.WriteOffAct;

import java.util.List;

public interface WriteOffActService {
    List<WriteOffAct> findAll();

    boolean save(WriteOffAct writeOffAct, Long locationId);

    WriteOffAct getById(Long id);

    void delete(WriteOffAct user);

    void deleteById(Long id);
}
