package com.itechart.retailers.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationItem that = (ApplicationItem) o;

        if (!Objects.equals(application, that.application)) return false;
        if (!Objects.equals(item, that.item)) return false;
        if (!Objects.equals(amount, that.amount)) return false;
        return Objects.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        int result = application != null ? application.hashCode() : 0;
        result = 31 * result + (item != null ? item.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        return result;
    }
}
