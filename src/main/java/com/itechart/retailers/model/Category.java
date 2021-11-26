package com.itechart.retailers.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category")
public class Category {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 45)
	private String name;


}
