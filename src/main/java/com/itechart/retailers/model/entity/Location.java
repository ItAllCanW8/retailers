package com.itechart.retailers.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    @ToString.Exclude
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<CustomerLocation> customerAssoc;
}
