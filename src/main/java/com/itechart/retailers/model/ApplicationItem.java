package com.itechart.retailers.model;

import com.itechart.retailers.model.util.ApplicationItemId;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "application_item")
public class ApplicationItem{
    @EmbeddedId
    ApplicationItemId id = new ApplicationItemId();

    @ManyToOne
    @MapsId("applicationId")
    private Application application;

    @ManyToOne
    @MapsId("itemId")
    private Item item;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "cost")
    private Float cost;
}
