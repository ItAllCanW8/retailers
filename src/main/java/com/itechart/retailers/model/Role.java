package com.itechart.retailers.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "role")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role extends Identity {

    @NotNull
    @Column(name = "role", nullable = false, length = 30)
    private String role;
}
