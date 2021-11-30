package com.itechart.retailers.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "application_item")
public class ApplicationItem extends Identity{
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "cost")
    private Float cost;
}
