package com.itechart.retailers.model.entity.projection;

import com.itechart.retailers.model.entity.Item;
import com.itechart.retailers.model.entity.WrittenOffItem;

import java.time.LocalDateTime;
import java.util.Set;

public interface WriteOffActView {

    Long getId();

    String getIdentifier();

    LocalDateTime getDateTime();

    Item getItem();

    Set<WrittenOffItem> getWrittenOffItems();
}
