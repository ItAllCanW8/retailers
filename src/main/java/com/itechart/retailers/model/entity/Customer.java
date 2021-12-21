package com.itechart.retailers.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class Customer extends Identity {

    @Column(name = "name")
    private String name;

    @Column(name = "registration_date")
    private LocalDate regDate;

    @Column(name = "active")
    private boolean isActive;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "customer_supplier",
            joinColumns = {@JoinColumn(name = "customer_id")},
            inverseJoinColumns = {@JoinColumn(name = "supplier_id")}
    )
    @ToString.Exclude
    Set<Supplier> suppliers = new HashSet<>();

    public Customer(Long customerId) {
        this.setId(customerId);
    }
}
