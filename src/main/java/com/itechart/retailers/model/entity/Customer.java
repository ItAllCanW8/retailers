package com.itechart.retailers.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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

    public Customer(Long customerId) {
        this.setId(customerId);
    }
}
