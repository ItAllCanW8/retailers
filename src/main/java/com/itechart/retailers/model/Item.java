package com.itechart.retailers.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "item")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Item extends Identity {
    @Column(name = "upc", length = 20)
    private String upc;

    @Column(name = "label", length = 45)
    private String label;

    @Column(name = "units")
    private Integer units;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private Set<ApplicationItem> applicationAssoc;
}
