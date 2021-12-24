package com.itechart.retailers.model.entity.projection;

import com.itechart.retailers.model.entity.BillItem;

import java.time.LocalDateTime;
import java.util.Set;

public interface BillView {

    Long getId();

    String getNumber();

    LocalDateTime getRegDateTime();

    Set<BillItem> getItemAssoc();
}
