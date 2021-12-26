package com.itechart.retailers.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer_category")
public class CustomerCategory extends Identity {

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "category_tax")
	private Float categoryTax;
}
