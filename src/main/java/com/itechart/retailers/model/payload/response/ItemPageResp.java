package com.itechart.retailers.model.payload.response;

import com.itechart.retailers.model.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ItemPageResp {
    private List<Item> items;
    private Integer totalPages;
}
