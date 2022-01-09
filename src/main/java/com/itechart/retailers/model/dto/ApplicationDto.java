package com.itechart.retailers.model.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto {
    private String applicationNumber;
    private String itemsTotal;
    private String unitsTotal;
    private String srcLocation;
    private String destLocation;
    private UserDto createdBy;
    private Set<ItemDto> items;
}
