package com.itechart.retailers.model.entity;

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
@Table(name = "item")
public class Item extends Identity {
    @Column(name = "upc", length = 20)
    private String upc;

    @Column(name = "label", length = 45)
    private String label;

    @Column(name = "units")
    private Integer units;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<ApplicationItem> applicationAssoc;
}
