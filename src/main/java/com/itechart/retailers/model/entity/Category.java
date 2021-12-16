package com.itechart.retailers.model.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "category")
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category extends Identity {

	@Column(name = "name", nullable = false, length = 45, unique = true)
	private String name;

}
