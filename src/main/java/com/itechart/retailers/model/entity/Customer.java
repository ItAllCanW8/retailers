package com.itechart.retailers.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
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

    //n+1 query
    /*
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id")
    @ToString.Exclude
    private User admin;*/

    @JsonManagedReference
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<CustomerLocation> locationAssoc;
}
