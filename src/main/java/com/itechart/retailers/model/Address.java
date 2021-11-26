package com.itechart.retailers.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "address")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address extends Identity {

    @Column(name = "state_code", length = 2)
    private String stateCode;

    @Column(name = "city")
    private String city;

    @Column(name = "first_line")
    private String firstLine;

    @Column(name = "second_line")
    private String secondLine;
}
