package com.itechart.retailers.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "location_item")
public class LocationItem extends Identity {

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "cost")
    private Float cost;

    @ManyToOne
    @JoinColumn(name = "location_id")
    @ToString.Exclude
    @JsonIgnore
    private Location location;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @ToString.Exclude
    @JsonIgnore
    private Item item;
}
