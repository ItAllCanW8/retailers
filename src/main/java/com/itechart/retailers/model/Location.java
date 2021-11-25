package com.itechart.retailers.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "location")
public class Location extends Identity {
    @Column(name = "identifier", nullable = false)
    private String identifier;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "total_capacity", nullable = false)
    private Integer totalCapacity;

    @Column(name = "available_capacity", nullable = false)
    private Integer availableCapacity;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
}
