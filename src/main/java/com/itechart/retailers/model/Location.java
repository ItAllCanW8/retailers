package com.itechart.retailers.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "location")
public class Location extends Identity {
    @Column(name = "identifier")
    private String identifier;

    @Column(name = "type")
    private String type;

    @Column(name = "total_capacity")
    private Integer totalCapacity;

    @Column(name = "available_capacity")
    private Integer availableCapacity;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
