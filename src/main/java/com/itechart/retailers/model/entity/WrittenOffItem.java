package com.itechart.retailers.model.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "written_off_item")
public class WrittenOffItem extends Identity{

    @Column(name = "amount")
    private Integer amount;

    @Enumerated(EnumType.STRING)
    private WriteOffReason reason;

    @ManyToOne
    @JoinColumn(name = "write_off_id")
    private WriteOffAct writeOffAct;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
