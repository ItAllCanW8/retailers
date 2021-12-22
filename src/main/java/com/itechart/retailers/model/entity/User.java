package com.itechart.retailers.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
public class User extends Identity {

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "login")
    private String login;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "active")
    private boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    @ToString.Exclude
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    @ToString.Exclude
    private Location location;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    private Customer customer;

    @JsonProperty(value = "customer")
    public Customer getCustomer() {
        return customer;
    }

    public User(Long id) {
        this.setId(id);
    }
}
