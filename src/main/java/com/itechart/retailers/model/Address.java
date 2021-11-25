package com.itechart.retailers.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address extends Identity {
    @Column(name = "state_code", length = 2, nullable = false)
    private String stateCode;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "first_line", nullable = false)
    private String firstLine;

    @Column(name = "second_line", nullable = false)
    private String secondLine;
}
