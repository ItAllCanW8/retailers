package com.itechart.retailers.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDto {

    private String number;
    private Long totalItemAmount;
    private Float totalItemSum;
    private LocalDateTime regDateTime;
}
