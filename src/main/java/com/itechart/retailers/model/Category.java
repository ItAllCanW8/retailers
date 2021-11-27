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

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;
}
