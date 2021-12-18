package com.itechart.retailers.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "application_item")
public class ApplicationItem extends Identity {

    @ManyToOne
    @JoinColumn(name = "application_id")
    @ToString.Exclude
    @JsonIgnore
    private Application application;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @ToString.Exclude
    @JsonIgnore
    private Item item;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "cost")
    private Float cost;

}
