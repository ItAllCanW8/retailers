package com.itechart.retailers.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "category")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category extends Identity {
    @Column(name = "name", nullable = false, length = 45)
    private String name;
}
