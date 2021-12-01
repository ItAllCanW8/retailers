package com.itechart.retailers.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "role")
public class Role extends Identity {

    @NotNull
    @Column(name = "role", nullable = false, length = 30)
    private String role;
}
