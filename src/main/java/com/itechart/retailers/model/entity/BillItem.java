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
@Table(name = "bill_item")
public class BillItem extends Identity {

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "price")
    private Float price;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
