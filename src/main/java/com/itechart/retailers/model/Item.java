package com.itechart.retailers.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "item")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "upc", nullable = false, unique = true, length = 20)
    private String upc;

    @Column(name = "label", nullable = false, length = 45)
    private String label;

    @Column(name = "units")
    private int units;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

}
