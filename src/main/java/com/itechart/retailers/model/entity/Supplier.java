package com.itechart.retailers.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "supplier")
public class Supplier extends Identity {

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "identifier", unique = true)
    private String identifier;

    @Column(name = "active")
    private boolean active;

    @ManyToMany(mappedBy = "suppliers")
    @ToString.Exclude
    private Set<Customer> customers = new HashSet<>();

    @OneToMany(mappedBy = "supplier")
    @ToString.Exclude
    private Set<Warehouse> warehouses = new HashSet<>();

    @JsonIgnore
    @JsonProperty(value = "customers")
    public Set<Customer> getCustomers() {
        return customers;
    }

    @JsonIgnore
    @JsonProperty(value = "warehouses")
    public Set<Warehouse> getWarehouses() {
        return warehouses;
    }
}
