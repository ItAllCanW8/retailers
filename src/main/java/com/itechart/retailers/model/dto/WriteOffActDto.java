package com.itechart.retailers.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WriteOffActDto {
    private String identifier;
    private Long totalItemAmount;
    private Float totalItemSum;
    private LocalDateTime dateTime;
}
