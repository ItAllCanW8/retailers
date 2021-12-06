package com.itechart.retailers.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer_location")
public class CustomerLocation extends Identity {

    @Column(name = "rental_tax_rate")
    private Float rentalTaxRate;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
}
