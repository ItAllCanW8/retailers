package com.itechart.retailers.model.entity;

import com.itechart.retailers.model.enumeration.StateCode;
import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "state_tax")
public class StateTax extends Identity {

    @Enumerated(EnumType.STRING)
    private StateCode stateCode;

    @Column(name = "tax")
    private Float tax;
}
