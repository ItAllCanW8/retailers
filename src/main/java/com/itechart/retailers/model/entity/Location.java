package com.itechart.retailers.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Column(name = "identifier", nullable = false, unique = true)
    private String identifier;

    @Column(name = "type")
    private String type;

    @Column(name = "total_capacity")
    private Integer totalCapacity;

    @Column(name = "rental_tax_rate")
    private Float rentalTaxRate;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @ToString.Exclude
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    private Customer customer;

    @JsonIgnore
    @JsonProperty(value = "customer")
    public Customer getCustomer() {
        return customer;
    }

    @OneToMany(mappedBy = "location", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<LocationItem> itemAssoc;

    public Location(Long id) {
        this.setId(id);
    }
}
