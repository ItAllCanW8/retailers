package com.itechart.retailers.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "item")
public class Item extends Identity {

    @Column(name = "upc", length = 20, unique = true, nullable = false)
    private String upc;

    @Column(name = "label", length = 45)
    private String label;

    @Column(name = "units")
    private Integer units;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    @ToString.Exclude
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<ApplicationItem> applicationAssoc = new HashSet<>();

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private Set<BillItem> itemAssoc = new HashSet<>();

    public Item(Long id) {
        this.setId(id);
    }
}
