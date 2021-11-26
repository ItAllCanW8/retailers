package com.itechart.retailers.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "item")
@AllArgsConstructor
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "upc", nullable = false, unique = true, length = 20)
	private String upc;

	@Column(name = "label", nullable = false, length = 45)
	private String label;

	@Column(name = "units")
	private int units;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id_fk")
	private Category category;

}
