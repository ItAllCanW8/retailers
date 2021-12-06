package com.itechart.retailers.model.entity;

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

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
}
